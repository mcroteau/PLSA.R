package shape.web;

import com.stripe.Stripe;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountLinkCreateParams;
import net.plsar.RouteAttributes;
import net.plsar.annotations.*;
import net.plsar.annotations.network.Get;
import net.plsar.annotations.network.Post;
import net.plsar.model.NetworkRequest;
import net.plsar.model.NetworkResponse;
import net.plsar.model.PageCache;
import net.plsar.security.SecurityManager;
import shape.Underscore;
import shape.model.*;
import shape.repo.BusinessRepo;
import shape.repo.TipRepo;
import shape.repo.TownRepo;
import shape.repo.UserRepo;
import shape.service.SeaService;
import shape.service.SmsService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BasicController {

    public BasicController(){
        this.smsService = new SmsService();
        this.seaService = new SeaService();
        this.underscore = new Underscore();
    }

    SmsService smsService;
    SeaService seaService;
    Underscore underscore;

    @Bind
    TipRepo tipRepo;

    @Bind
    TownRepo townRepo;

    @Bind
    UserRepo userRepo;

    @Bind
    BusinessRepo businessRepo;

    @Get("/")
    public String index(NetworkRequest req, SecurityManager security){
        if(security.isAuthenticated(req)){
            return "redirect:/snapshot";
        }
        return "redirect:/home";
    }

    @Design("/designs/guest.jsp")
    @Get("/{guid}")
    public String giveTip(PageCache cache,
                          @Component String guid){
        String GUID = guid.toUpperCase();
        User recipient = userRepo.getGuid(GUID);
        if(recipient == null){
            cache.set("message", "We were unable to find recipient! Please give it another go!");
            return "redirect:/discover";
        }
        cache.set("title", "Send Tip " + recipient.getName());
        cache.set("recipient", recipient);
        return "/pages/tip/index.jsp";
    }

    @Design("/designs/guest.jsp")
    @Get("/asset/{uuid}")
    public String asset(PageCache cache,
                        @Component String uuid){
        String UUID = uuid.toUpperCase();
        User recipient = userRepo.getUuid(UUID);
        if(recipient == null){
            cache.set("message", "We were unable to find recipient! Please give it another go!");
            return "redirect:/discover";
        }
        cache.set("title", "Send Tip " + recipient.getName());
        cache.set("recipient", recipient);
        return "/pages/tip/index.jsp";
    }

    @Meta(title = "Home!")
    @Design("/designs/guest.jsp")
    @Get("/home")
    public String home(){
        return "/pages/home.jsp";
    }

    @Meta(title="Employment")
    @Design("/designs/guest.jsp")
    @Get("/employment")
    public String employment(NetworkRequest req,
                             SecurityManager security,
                             PageCache data){
        if(!security.isAuthenticated(req)){
            data.set("message", "Please signin to continue!");
            return "redirect:/signin";
        }
        String credential = security.getUser(req);
        User authUser = userRepo.getPhone(credential);
        if(authUser == null){
            authUser = userRepo.getEmail(credential);
        }
        List<UserBusiness> userBusinesses = userRepo.getBusinesses(authUser.getId());

        List<Business> businesses = new ArrayList<>();
        for(UserBusiness userBusiness: userBusinesses){
            Business business = businessRepo.get(userBusiness.getBusinessId());
            business.setUserBusiness(userBusiness);
            businesses.add(business);
        }
        data.set("businesses", businesses);
        return "/pages/business/index.jsp";
    }

    @Meta(title="Snapshot!")
    @Design("/designs/guest.jsp")
    @Get("/snapshot")
    public String snapshot(NetworkRequest req,
                           SecurityManager security,
                           PageCache cache){
        if(!security.isAuthenticated(req)){
            cache.set("message", "Please signin to continue!");
            return "redirect:/signin";
        }

        if(security.hasRole(underscore.getSuperRole(), req)){
            List<User> users = userRepo.getList();
            cache.set("users", users);

            List<SignUpRequest> requests = businessRepo.getSignupRequests();
            cache.set("requests", requests);
            cache.set("page", "/pages/super/index.jsp");
        }

        if(!security.hasRole(underscore.getSuperRole(), req)){
            String credential = security.getUser(req);
            User authUser = userRepo.getPhone(credential);
            if(authUser == null){
                authUser = userRepo.getEmail(credential);
            }
            List<Tip> tips = tipRepo.getList(authUser.getId());
            for (Tip tip : tips) {
                User patron = userRepo.get(tip.getPatronId());
                tip.setEmail(patron.getEmail());
            }
            cache.set("tips", tips);
        }
        return "/pages/business/snapshot.jsp";
    }

    @Design("/designs/guest.jsp")
    @Get("/discover")
    public String discover(NetworkRequest req,
                           PageCache cache){

        List<Town> towns = townRepo.getList();
        if(req.getValue("z") != null &&
                !req.getValue("z").equals("Select a Town/City")){
            Long townId = Long.parseLong(req.getValue("z"));
            List<Business> businesses = businessRepo.getList(townId);
            cache.set("townId", townId);
            cache.set("businesses", businesses);
        }
        if(req.getValue("zq") != null &&
                 !req.getValue("zq").equals("Select One")){
            Long businessId = Long.parseLong(req.getValue("zq"));
            Business business = businessRepo.get(businessId);
            cache.set("business", business);
            List<UserBusiness> userBusinesses = userRepo.getUsers(businessId);
            List<User> users = new ArrayList<>();
            for(UserBusiness userBusiness : userBusinesses){
                User user = userRepo.get(userBusiness.getUserId());
                user.setUserBusiness(userBusiness);
                if(user.getName() != null) {
                    String[] bits = user.getName().split(" ");
                    String firstBit = bits[0];
                    char first = firstBit.toCharArray()[0];
                    char second = 0;
                    if(bits.length > 1) {
                        String secondBit = bits[1];
                        second = secondBit.toCharArray()[0];
                    }
                    String initials = first + "" + second;
                    user.setInitials(initials);
                }
                users.add(user);
            }
            cache.set("people", users);
        }
        if(req.getValue("lat") != null)cache.set("lat", req.getValue("lat"));
        if(req.getValue("lon") != null)cache.set("lon", req.getValue("lon"));

        cache.set("title", "Person Locator!");
        cache.set("towns", towns);
        return "/pages/discover.jsp";
    }

    @Get("/signup_request")
    public String signupRequest(PageCache cache){
        cache.set("title", "Business Request");
        cache.set("page", "/pages/signup_request.jsp");
        return "/designs/guest.jsp";
    }

    @Post("/signup_request/save")
    public String saveRequest(NetworkRequest req,
                         PageCache data){
        SignUpRequest signUpRequest = (SignUpRequest) req.inflect(SignUpRequest.class);
        if(signUpRequest.getAddress() == null){
            data.set("message", "Please tell us where you would like us to add data.");
            return "redirect:/signup_request";
        }
        if(signUpRequest.getName() == null){
            data.set("message", "Please tell us what business you would like to see on our system.");
            return "redirect:/signup_request";
        }
        businessRepo.saveSignupRequest(signUpRequest);
        try{
            RouteAttributes routeAttributes = req.getRouteAttributes();
            String key = (String) routeAttributes.get("sms.key");
            smsService.send("9079878652", signUpRequest.getName() + " @ " + signUpRequest.getAddress(), key);
        }catch (Exception ex){}
        data.set("message", "Success! Thank you! We are on it! Your request is in our system!");
        return "redirect:/home";
    }

    @Post("/signup_request/delete/{id}")
    public String deleteRequest(NetworkRequest req,
                                SecurityManager security,
                                PageCache cache,
                                @Component Long id){
        if(!security.isAuthenticated(req)){
            cache.set("message", "Whao! Where are you going.");
            return "redirect:/";
        }
        if(!security.hasRole(underscore.getSuperRole(), req)){
            cache.set("message", "Whao! Where are you going.");
            return "redirect:/";
        }
        businessRepo.deleteSignupRequest(id);
        cache.set("message", "Successfully deleted signup request");
        return "redirect:/snapshot";
    }

    @Post("/stripe/activate/{id}")
    public String activateAccount(NetworkRequest req,
                                  NetworkResponse resp,
                                  SecurityManager security,
                                  PageCache cache,
                                  @Component Long id) {
        if(!security.isAuthenticated(req)){
            return "redirect:/";
        }

        AccountCreateParams accountParams =
                AccountCreateParams.builder()
                        .setType(AccountCreateParams.Type.STANDARD)
                        .build();

        try {

            RouteAttributes routeAttributes = req.getRouteAttributes();
            String apiKey = (String) routeAttributes.get("stripe.key");
            String host = (String) routeAttributes.get("system.host");

            String refreshUrl = host + "/noop";
            String returnUrl = host + "/stripe/activated/" + id;

            Stripe.apiKey = apiKey;

            Account account = Account.create(accountParams);
            AccountLinkCreateParams linkParams =
                    AccountLinkCreateParams.builder()
                            .setAccount(account.getId())
                            .setRefreshUrl(refreshUrl)
                            .setReturnUrl(returnUrl)
                            .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                            .build();

            AccountLink accountLink = AccountLink.create(linkParams);

            User user = userRepo.get(id);
            user.setStripeAccountId(account.getId());
            userRepo.update(user);

            resp.setRedirectLocation(accountLink.getUrl());

        }catch(Exception ex){
            ex.printStackTrace();
            cache.set("message", "We are sorry! Something needs to be fixed. Will you contact us and let us know?");
            return "redirect:/";
        }

        return "";
    }

    @Get("/stripe/activated/{id}")
    public String activated(PageCache data,
                            @Component Long id){
        User user = userRepo.get(id);
        user.setActivated(true);
        userRepo.update(user);
        data.set("message", "Successfully completed on boarding! You are ready to now accept tips!");
        return "redirect:/signin";
    }

    @Design("/designs/guest.jsp")
    @Get("/employment/create")
    public String createEmployment(NetworkRequest req,
                                   SecurityManager security,
                                   PageCache cache){
        if(!security.isAuthenticated(req)){
            cache.set("message", "Please signin to continue..");
            return "redirect:/signin";
        }
        List<Town> towns = townRepo.getList();
        if(req.getValue("z") != null &&
                !req.getValue("z").equals("Select a Town/City")){
            Long townId = Long.parseLong(req.getValue("z"));
            List<Business> businesses = businessRepo.getList(townId);
            cache.set("townId", townId);
            cache.set("businesses", businesses);
        }

        if(req.getValue("lat") != null)cache.set("lat", req.getValue("lat"));
        if(req.getValue("lon") != null)cache.set("lon", req.getValue("lon"));

        cache.set("towns", towns);
        return "/pages/business/create.jsp";
    }


    @Post("/employment/save")
    public String saveEmployment(NetworkRequest req,
                                 SecurityManager security,
                                 PageCache cache){
        if(!security.isAuthenticated(req)){
            cache.set("message", "Please signin to continue..");
            return "redirect:/signin";
        }
        UserBusiness userBusiness = (UserBusiness) req.inflect(UserBusiness.class);
        if(userBusiness.getUserId() == null){
            cache.set("message", "Will you contact us, something spooky just happened. Thank you!");
            return "redirect:/employment/create";
        }
        if(userBusiness.getBusinessId() == null){
            cache.set("message", "Please select a business in order to continue...");
            return "redirect:/employment/create";
        }
        userRepo.saveBusiness(userBusiness);
        cache.set("message", "Successfully added employer! Now feel free to update its details.");
        return "redirect:/employment";
    }

    @Get("/businesses/enable/{id}")
    public String iworkhere(NetworkRequest req,
                            SecurityManager security,
                            PageCache data,
                            @Component Long id){
        if(!security.isAuthenticated(req)){
            return "redirect:/";
        }
        String permission = underscore.getBusinessMaintenance() + id;
        if(!security.hasRole(underscore.getSuperRole(), req) &&
                !security.hasPermission(permission, req)){
            return "redirect:/";
        }
        UserBusiness userBusiness = userRepo.getBusiness(id);
        userBusiness.setActive(true);
        userRepo.updateBusiness(userBusiness);
        data.set("message", "Successfully updated work status!");
        return "redirect:/employment";
    }

    @Get("/businesses/disable/{id}")
    public String iquit(NetworkRequest req,
                        SecurityManager security,
                        PageCache data,
                        @Component Long id){
        if(!security.isAuthenticated(req)){
            return "redirect:/";
        }
        String permission = underscore.getBusinessMaintenance() + id;
        if(!security.hasRole(underscore.getSuperRole(), req) &&
                !security.hasPermission(permission, req)){
            return "redirect:/";
        }
        UserBusiness userBusiness = userRepo.getBusiness(id);
        userBusiness.setActive(false);
        userRepo.updateBusiness(userBusiness);
        data.set("message", "Successfully updated work status!");
        return "redirect:/employment";
    }

    @Design("/designs/guest.jsp")
    @Get("/businesses/edit/{id}")
    public String edit(NetworkRequest req,
                       SecurityManager security,
                       PageCache cache,
                       @Component Long id){
        if(!security.isAuthenticated(req)){
            return "redirect:/";
        }
        String permission = underscore.getBusinessMaintenance() + id;
        if(!security.hasRole(underscore.getSuperRole(), req) &&
                !security.hasPermission(permission, req)){
            return "redirect:/";
        }
        UserBusiness userBusiness = userRepo.getBusiness(id);
        Business business = businessRepo.get(userBusiness.getBusinessId());
        userBusiness.setBusiness(business);
        cache.set("business", userBusiness);
        return "/pages/business/edit.jsp";
    }

    @Post("/businesses/update")
    public String update(NetworkRequest req,
                         SecurityManager security,
                         PageCache cache){
        if(!security.isAuthenticated(req)){
            return "redirect:/";
        }
        UserBusiness userBusiness = (UserBusiness) req.inflect(UserBusiness.class);
        String permission = underscore.getBusinessMaintenance() + userBusiness.getId();
        if(!security.hasRole(underscore.getSuperRole(), req) &&
                !security.hasPermission(permission, req)){
            return "redirect:/";
        }
        String partTime = req.getValue("partTime");
        if(partTime == null){
            userBusiness.setPartTime(false);
        }else if(partTime.equals("on")){
            userBusiness.setPartTime(true);
        }
        userRepo.updateBusiness(userBusiness);
        cache.set("message", "Successfully updated business details!");
        return "redirect:/businesses/edit/" + userBusiness.getId();
    }


    @Get("/noop")
    public String noop(PageCache data){
        data.set("message", "noop!");
        data.set("page", "/pages/home.jsp");
        return "designs/guest.jsp";
    }
}
