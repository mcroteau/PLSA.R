package shape.service;

import com.google.gson.Gson;
import com.stripe.Stripe;
import qio.Qio;
import qio.annotate.Inject;
import qio.annotate.Property;
import qio.annotate.Service;
import qio.model.web.PageCache;
import shape.Underscore;
import shape.model.*;
import shape.repo.TipRepo;
import shape.repo.StripeRepo;
import shape.repo.UserRepo;


@Service
public class DonationService {

    Gson gson = new Gson();

    @Bind
    Qio qio;

    @Bind
    UserRepo userRepo;

    @Bind
    StripeRepo stripeRepo;

    @Bind
    TipRepo tipRepo;

    @Bind
    AuthService authService;

    @Bind
    SmsService smsService;

    @Property("stripe.apiKey")
    String apiKey;


    public String getUserPermission(String id){
        return Underscore.USER_MAINTENANCE + id;
    }

    public String index(PageCache data) {
        data.set("inDonateMode", true);
        return "/pages/donate/index.jsp";
    }

    public String cancel(String subscriptionId, PageCache data){
        try{

            Stripe.apiKey = apiKey;
            com.stripe.model.Subscription subscription = com.stripe.model.Subscription.retrieve(subscriptionId);
            subscription.cancel();

            Tip tip = tipRepo.get(subscriptionId);
            tip.setCancelled(true);
            tipRepo.update(tip);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return gson.toJson(data);
    }
}
