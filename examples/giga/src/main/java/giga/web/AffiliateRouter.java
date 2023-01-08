package giga.web;

import giga.service.AffiliateService;
import net.plsar.annotations.*;
import net.plsar.annotations.network.Get;
import net.plsar.annotations.network.Post;
import net.plsar.model.NetworkRequest;
import net.plsar.model.PageCache;
import net.plsar.security.SecurityManager;

@Controller
public class AffiliateRouter {

    @Bind
    AffiliateService affiliateService;

    @Meta(design = "/designs/auth.jsp")
    @Get("/affiliates/{id}")
    public String getAffiliates(PageCache cache,
                                NetworkRequest req,
                                SecurityManager security,
                                @Component Long id){
        return affiliateService.getAffiliates(id, cache, req, security);
    }

    @Design("/designs/guest.jsp")
    @Get("/affiliates/onboarding")
    public String getOnboarding(NetworkRequest req,
                                SecurityManager security,
                                PageCache cache){
        return affiliateService.getOnboarding(cache, req);
    }

    @Design("/designs/auth.jsp")
    @Get("/affiliates/requests/{id}")
    public String getRequests(PageCache cache,
                              NetworkRequest req,
                              SecurityManager security,
                              @Component Long id){
        return affiliateService.getRequests(id, cache, req, security);
    }

    @Design("/designs/partners.jsp")
    @Get("/affiliates/onboarding/status/{guid}")
    public String status(PageCache cache,
                         @Component String guid){
        return affiliateService.status(guid, cache);
    }

    @Post("/affiliates/onboarding/begin")
    public String begin(PageCache cache,
                        NetworkRequest req){
        return affiliateService.begin(cache, req);
    }

    @Post("/affiliates/onboarding/approve/{id}")
    public String approve(PageCache cache,
                          NetworkRequest req,
                          SecurityManager security,
                          @Component Long id){
        return affiliateService.approve(id, cache, req, security);
    }

    @Post("/affiliates/onboarding/pass/{guid}")
    public String deny(PageCache cache,
                       NetworkRequest req,
                       SecurityManager security,
                       @Component Long id){
        return affiliateService.deny(id, cache, req, security);
    }

    @Post("/affiliate/setup/{id}")
    public String setupAffiliate(PageCache cache,
                                 NetworkRequest req,
                                 SecurityManager security,
                                 @Component Long id){
        return affiliateService.setupAffiliate(id);
    }

    @Design("/designs/partners.jsp")
    @Get("/affiliates/onboarding/finalize/{id}")
    public String finalizeOnboarding(PageCache cache,
                                     @Component Long id){
        return affiliateService.finalizeOnboarding(id, cache);
    }

    @Post("/affiliates/onboarding/finalize/{id}")
    public String finalizeOnboarding(PageCache cache,
                                     NetworkRequest req,
                                     SecurityManager security) {
        return affiliateService.finalizeOnboarding(cache, req, security);
    }
}
