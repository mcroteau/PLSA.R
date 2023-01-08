package giga.web;

import giga.service.PageService;
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
public class PageRouter {

    @Bind
    PageService pageService;

    @Get("/{business}")
    public String getHome(PageCache cache,
                          NetworkRequest req,
                          SecurityManager security,
                          @Component String business){
        return pageService.getPage(business.toLowerCase(), "home", cache, req, security);
    }

    @Get("/{business}/asset/{page}")
    public String getPage(PageCache cache,
                          NetworkRequest req,
                          SecurityManager security,
                          @Component String business,
                          @Component String page){
        System.out.println("get page");
        return pageService.getPage(business.toLowerCase(), page.toLowerCase(), cache, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/pages/new/{businessId}")
    public String configure(PageCache cache,
                            NetworkRequest req,
                            SecurityManager security,
                            @Component Long businessId){
        return pageService.create(businessId, cache, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/pages/{businessId}")
    public String list(PageCache cache,
                       NetworkRequest req,
                       SecurityManager security,
                       @Component Long businessId){
        return pageService.list(businessId, cache, req, security);
    }

    @Post("/pages/save")
    public String save(PageCache cache,
                       NetworkRequest req,
                       SecurityManager security) throws Exception {
        return pageService.save(req, security);
    }

    @Post("/pages/delete/{businessId}/{id}")
    public String delete(PageCache cache,
                         NetworkRequest req,
                         SecurityManager security,
                         @Component Long businessId,
                         @Component Long id){
        return pageService.delete(id, businessId, cache, req, security);
    }

}
