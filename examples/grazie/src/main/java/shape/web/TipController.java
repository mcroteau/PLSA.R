package shape.web;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import com.stripe.param.SubscriptionCreateParams;
import net.plsar.RouteAttributes;
import net.plsar.annotations.Bind;
import net.plsar.annotations.Component;
import net.plsar.annotations.Controller;
import net.plsar.annotations.Design;
import net.plsar.annotations.network.Get;
import net.plsar.annotations.network.Post;
import net.plsar.model.NetworkRequest;
import net.plsar.model.PageCache;
import net.plsar.security.SecurityManager;
import shape.Grazie;
import shape.model.*;
import shape.repo.TipRepo;
import shape.repo.StripeRepo;
import shape.repo.UserRepo;
import shape.service.SeaService;
import shape.service.SmsService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TipController {

    public TipController(){
        this.smsService = new SmsService();
        this.seaService = new SeaService();
        this.grazie = new Grazie();
    }

    SmsService smsService;
    SeaService seaService;
    Grazie grazie;

    @Bind
    UserRepo userRepo;

    @Bind
    StripeRepo stripeRepo;

    @Bind
    TipRepo tipRepo;

    @Design("/designs/guest.jsp")
    @Get("/tip/{id}")
    public String tip(PageCache cache,
                      @Component Long id){
        User recipient = userRepo.get(id);
        if(recipient == null){
            cache.set("message", "We were unable to find recipient! Please give it another go!");
            return "redirect:/discover";
        }
        cache.set("title", "Send Tip " + recipient.getName());
        cache.set("recipient", recipient);
        return "/pages/tip/index.jsp";
    }

    @Post("/tip/{id}")
    public String execute(NetworkRequest req,
                          SecurityManager security,
                          PageCache data,
                          @Component Long id){

        Tip tip = (Tip) req.inflect(Tip.class);
        User recipient = userRepo.get(id);
        if(recipient == null){
            data.set("message", "Forgive us, something went wrong. Please contact one of us.");
            return "redirect:/home";
        }

        if(tip.getAmount() == null){
            data.set("message", "Please enter an amount you would like to tip!");
            return "redirect:/" + recipient.getGuid();
        }

        tip.setEmail(grazie.getSpaces(tip.getEmail()));
        if(!grazie.isValidMailbox(tip.getEmail())){
            data.set("message", "Please enter a valid Email Address!");
            return "redirect:/" + recipient.getGuid();
        }
        tip.setCreditCard(grazie.getSpaces(tip.getCreditCard()));
        if(tip.getCreditCard().equals("")){
            data.set("message", "Please enter a valid credit card!");
            return "redirect:/" + recipient.getGuid();
        }
        if(tip.getExpMonth().equals("")){
            data.set("message", "Please enter a valid Expiration Month!");
            return "redirect:/" + recipient.getGuid();
        }
        if(tip.getExpYear().equals("")){
            data.set("message", "Please enter a valid Expiration Year!");
            return "redirect:/" + recipient.getGuid();
        }
        if(tip.getCvc().equals("")){
            data.set("message", "Please enter a valid Credit Card CVC code");
            return "redirect:/" + recipient.getGuid();
        }


        try {

            RouteAttributes routeAttributes = req.getRouteAttributes();
            String apiKey = (String) routeAttributes.get("stripe.key");

            Stripe.apiKey = apiKey;

            String guid = grazie.getTip(36).toLowerCase();
            tip.setGuid(guid);

            User patron = userRepo.get(tip.getEmail());

            if (patron == null) {
                patron = new User();
                patron.setEmail(tip.getEmail());
                patron.setPassword(security.hash(guid));
                patron.setClean(guid);
                patron.setDateCreated(grazie.getDate());
                userRepo.save(patron);
                patron = userRepo.getSaved();
                patron.setClean(guid);

                userRepo.saveUserRole(patron.getId(), grazie.getDonorRole());

                String permission = grazie.getUserMaintenance() + patron.getId();
                userRepo.savePermission(patron.getId(), permission);
            }

            tip.setPatronId(patron.getId());
            tip.setProcessed(false);
            tip.setTipDate(grazie.getDate());//2.9% goes to another company

            BigDecimal appfee = tip.getAmount().multiply(new BigDecimal(0.07, new MathContext(2)));
            Long appfeecents = appfee.movePointRight(2).longValue();
            BigDecimal amountafter = tip.getAmount().subtract(appfee);
            Long amountcentsafter = amountafter.movePointRight(2).longValue();

            Long amountCents = tip.getAmount().movePointRight(2).longValue();
            tip.setAmountCents(amountcentsafter);

            tipRepo.save(tip);


            tip.setStatus("Processing");
            tip.setMessage("hasn't processed yet...");

            Map<String, Object> card = new HashMap<>();
            card.put("number", tip.getCreditCard());
            card.put("exp_month", tip.getExpMonth());
            card.put("exp_year", tip.getExpYear());
            card.put("cvc", tip.getCvc());
            Map<String, Object> params = new HashMap<>();
            params.put("card", card);

            RequestOptions tokenRequestOptions = RequestOptions.builder()
                    .setStripeAccount(recipient.getStripeAccountId())
                    .build();
            Token token = Token.create(params, tokenRequestOptions);


            Customer customer = null;
            if (patron.getStripeCustomerId() != null &&
                    !patron.getStripeCustomerId().equals("")) {
                try {
                    customer = Customer.retrieve(patron.getStripeCustomerId());
                } catch (Exception e) {
                    System.out.println("stale stripe customer id");
                }
            }

            if(customer == null) {
                Map<String, Object> customerParams = new HashMap<>();
                customerParams.put("email", tip.getEmail());
                customerParams.put("source", token.getId());
                RequestOptions requestOptions = RequestOptions.builder()
                        .setStripeAccount(recipient.getStripeAccountId())
                        .build();
                customer = com.stripe.model.Customer.create(customerParams, requestOptions);
            }

            patron.setStripeCustomerId(customer.getId());
            userRepo.update(patron);

            String nickname = getDescription(tip, recipient);

            Boolean chargeSuccess = false;
            Boolean subscriptionSuccess = false;

            tip = tipRepo.getGuid(tip.getGuid());

            if (tip.isRecurring()) {

                DynamicsPrice storedPrice = stripeRepo.getPriceAmount(tip.getAmount());

                if (storedPrice != null) {
                    Price price = null;
                    try {
                        price = com.stripe.model.Price.retrieve(storedPrice.getStripeId());
                    }catch(StripeException ex){ }
                    if (price == null) {
                        generateStripePrice(amountCents, storedPrice, recipient);
                        storedPrice = stripeRepo.getPrice(storedPrice.getId());
                    }
                    subscriptionSuccess = createSubscription(tip, storedPrice, customer, recipient);
                }

                if (storedPrice == null) {

                    DynamicsPrice dynamicsPrice = new DynamicsPrice();
                    dynamicsPrice.setAmount(tip.getAmount());
                    dynamicsPrice.setNickname(nickname);

                    Map<String, Object> productParams = new HashMap<>();
                    productParams.put("name", dynamicsPrice.getNickname());

                    com.stripe.model.Product stripeProduct = null;
                    if(recipient != null) {
                        RequestOptions requestOptions = RequestOptions.builder().setStripeAccount(recipient.getStripeAccountId()).build();
                        stripeProduct = com.stripe.model.Product.create(productParams, requestOptions);
                    }else{
                        data.set("message", "We need to resolve an issue. Nothing was charged. Forgive us.");
                        return "redirect:/" + recipient.getGuid();
                    }

                    DynamicsProduct dynamicsProduct = new DynamicsProduct();
                    dynamicsProduct.setNickname(dynamicsPrice.getNickname());
                    dynamicsProduct.setStripeId(stripeProduct.getId());
                    DynamicsProduct savedProduct = stripeRepo.saveProduct(dynamicsProduct);

                    Price stripePrice = genStripeRecurringPrice(amountCents, dynamicsPrice, stripeProduct, recipient);
                    if (stripePrice == null) {
                        data.set("message", "Something went wrong on our end. We are sorry! Please contact us. (907) 987-8652");
                        return "redirect:/" + recipient.getGuid();
                    }

                    dynamicsPrice.setStripeId(stripePrice.getId());
                    dynamicsPrice.setProductId(savedProduct.getId());
                    DynamicsPrice savedPrice = stripeRepo.savePrice(dynamicsPrice);

                    subscriptionSuccess = createSubscription(tip, savedPrice, customer, recipient);

                }
            }

            if (!tip.isRecurring()) {

                Map<String, Object> chargeParams = new HashMap<>();
                chargeParams.put("amount", amountcentsafter);
                chargeParams.put("customer", customer.getId());
                chargeParams.put("card", token.getCard().getId());
                chargeParams.put("currency", "usd");
                chargeParams.put("application_fee_amount", appfeecents);

                com.stripe.model.Charge charge;
                if(recipient != null) {
                    RequestOptions requestOptions = RequestOptions.builder().setStripeAccount(recipient.getStripeAccountId()).build();
                    charge = com.stripe.model.Charge.create(chargeParams, requestOptions);
                }else{
                    data.set("message", "We need to resolve an issue. Nothing was charged. Forgive us.");
                    return "redirect:/" + recipient.getGuid();
                }

                tip.setChargeId(charge.getId());
                tipRepo.update(tip);

                patron.setStripeCustomerId(customer.getId());
                userRepo.update(patron);

                chargeSuccess = true;
            }

            if (chargeSuccess || subscriptionSuccess) {
                tip.setProcessed(true);
                tipRepo.update(tip);
            }

            if(!chargeSuccess &&
                    !subscriptionSuccess){
                data.set("message", "We are sorry, something went kabonk on our end. Please try again!");
                return "redirect:/" + recipient.getGuid();
            }

            String key = (String) routeAttributes.get("sms.key");

            if(recipient.getPhone() != null) {
                try {
                    String payload = "Grazie! ~ $" + tip.getAmount() + " tip! Here is the tipper's email, " + tip.getEmail();
                    smsService.send(recipient.getPhone(), payload, key);
                }catch(Exception ex){}
            }

            try {
                smsService.send("9079878652", "Tip!", key);
            }catch(Exception ex){}

        }catch(StripeException ex){
            data.set("message", ex.getMessage());
            return "redirect:/" + recipient.getGuid();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/tips/" + tip.getGuid();
    }


    @Design("/designs/guest.jsp")
    @Get("/tips/{guid}")
    public String tip(PageCache cache,
                      @Component String guid){
        String GUID = guid.toLowerCase();
        Tip tip = tipRepo.getGuid(GUID);
        if(tip == null){
            cache.set("message", "We were unable to find tip!");
            return "redirect:/";
        }
        User recipient = userRepo.get(tip.getRecipientId());
        tip.setRecipient(recipient);
        cache.set("tip", tip);
        return "/pages/tip/status.jsp";
    }



    private Price generateStripePrice(Long amountInCents, DynamicsPrice storedPrice, User recipient) throws StripeException {
        DynamicsProduct dynamicsProduct = stripeRepo.getProduct(storedPrice.getProductId());
        Map<String, Object> productParams = new HashMap<>();
        productParams.put("name", storedPrice.getNickname());

        com.stripe.model.Product stripeProduct = null;
        if(recipient != null) {
            RequestOptions requestOptions = RequestOptions.builder().setStripeAccount(recipient.getStripeAccountId()).build();
            stripeProduct = com.stripe.model.Product.create(productParams, requestOptions);
        }

        dynamicsProduct.setStripeId(stripeProduct.getId());
        stripeRepo.updateProduct(dynamicsProduct);

        Map<String, Object> recurring = new HashMap<>();
        recurring.put("interval", "month");

        Map<String, Object> priceParams = new HashMap<>();
        priceParams.put("product", stripeProduct.getId());
        priceParams.put("unit_amount", amountInCents);
        priceParams.put("currency", "usd");
        priceParams.put("recurring", recurring);
        RequestOptions requestOptions = RequestOptions.builder().setStripeAccount(recipient.getStripeAccountId()).build();
        Price stripePrice = Price.create(priceParams, requestOptions);

        storedPrice.setStripeId(stripePrice.getId());
        stripeRepo.updatePrice(storedPrice);

        return stripePrice;
    }

    private Price genStripeRecurringPrice(Long amountInCents, DynamicsPrice dynamicsPrice, Product stripeProduct, User recipient) throws StripeException {
        Map<String, Object> recurring = new HashMap<>();
        recurring.put("interval", dynamicsPrice.getFrequency());

        Map<String, Object> priceParams = new HashMap<>();
        priceParams.put("product", stripeProduct.getId());
        priceParams.put("unit_amount", amountInCents);
        priceParams.put("currency", dynamicsPrice.getCurrency());
        priceParams.put("recurring", recurring);
        RequestOptions requestOptions = RequestOptions.builder().setStripeAccount(recipient.getStripeAccountId()).build();
        Price stripePrice = Price.create(priceParams, requestOptions);
        return stripePrice;
    }

    private boolean createSubscription(Tip tip, DynamicsPrice dynamicsPrice, Customer customer, User recipient) throws StripeException {

        SubscriptionCreateParams params = SubscriptionCreateParams.builder()
                .setCustomer(customer.getId())
                .addItem(SubscriptionCreateParams.Item.builder()
                        .setPrice(dynamicsPrice.getStripeId())
                        .build())
                .setApplicationFeePercent(new BigDecimal(2.9, new MathContext(1)))
                .addExpand("latest_invoice.payment_intent")
                .build();
        com.stripe.model.Subscription subscription = null;
        if(recipient != null) {
            RequestOptions requestOptions = RequestOptions.builder().setStripeAccount(recipient.getStripeAccountId()).build();
            subscription = com.stripe.model.Subscription.create(params, requestOptions);
        }

        tip.setSubscriptionId(subscription.getId());
        tipRepo.update(tip);

        return true;
    }

    private String getDescription(Tip tip, User user){
        String recurring = tip.isRecurring() ? "Month" : "";
        return "$" + tip.getAmount() + " " + recurring;
    }

}
