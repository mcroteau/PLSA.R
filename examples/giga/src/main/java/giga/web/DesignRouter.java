package giga.web;

import giga.service.DesignService;
import net.plsar.annotations.Component;
import net.plsar.annotations.Design;
import net.plsar.annotations.Controller;
import net.plsar.annotations.Bind;
import net.plsar.annotations.http.Get;
import net.plsar.annotations.http.Post;
import net.plsar.model.PageCache;
import net.plsar.model.NetworkRequest;
import net.plsar.security.SecurityManager;

@Controller
public class DesignRouter {

    @Bind
    DesignService designService;

    @Design("/designs/auth.jsp")
    @Get("/designs/new/{id}")
    public String configure(PageCache cache,
                            NetworkRequest req,
                            SecurityManager security,
                            @Component Long id){
        return designService.configure(id, cache, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/designs/{id}")
    public String list(PageCache cache,
                       NetworkRequest req,
                       SecurityManager security,
                       @Component Long id) throws Exception{
        return designService.list(id, cache, req, security);
    }

    @Post("/designs/save")
    public String save(NetworkRequest req,
                       SecurityManager security){
        return designService.save(req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/designs/edit/{id}")
    public String showcase(PageCache cache,
                           NetworkRequest req,
                           SecurityManager security,
                           @Component Long id) throws Exception {
        return designService.edit(id, cache, req, security);
    }

    @Post("/designs/update/{id}")
    public String update(PageCache cache,
                         NetworkRequest req,
                         SecurityManager security,
                         @Component Long id){
        return designService.update(id, cache, req, security);
    }

    @Post("/designs/delete/{id}")
    public String delete(PageCache cache,
                         NetworkRequest req,
                         SecurityManager security,
                         @Component Long id){
        return designService.delete(id, cache, req, security);
    }

}
