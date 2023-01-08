package giga.web;

import giga.service.SaleService;
import net.plsar.annotations.Component;
import net.plsar.annotations.Design;
import net.plsar.annotations.Controller;
import net.plsar.annotations.Bind;
import net.plsar.annotations.network.Get;
import net.plsar.annotations.network.Post;
import net.plsar.model.PageCache;
import net.plsar.model.NetworkRequest;
import net.plsar.security.SecurityManager;

@Controller
public class SaleRouter {

    @Bind
    SaleService saleService;

    @Design("/designs/auth.jsp")
    @Get("/sales/{businessId}")
    public String list(PageCache cache,
                       NetworkRequest req,
                       SecurityManager security,
                       @Component Long businessId) throws Exception{
        return saleService.list(businessId, cache, req, security);
    }

    @Post("/{business}/sale/{id}")
    public String processSale(PageCache cache,
                              NetworkRequest req,
                              @Component String businessUri,
                              @Component Long id){
        return saleService.processSale(id, businessUri.toLowerCase(), req);
    }

    @Get("/{business}/sale/{id}")
    public String getSale(PageCache cache,
                          NetworkRequest req,
                          SecurityManager security,
                          @Component String businessUri,
                          @Component Long id){
        return saleService.getSale(id, businessUri.toLowerCase(), cache, req, security);
    }

}
