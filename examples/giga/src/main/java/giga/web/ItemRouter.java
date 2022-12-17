package giga.web;

import giga.service.ItemService;
import net.plsar.annotations.Component;
import net.plsar.annotations.Design;
import net.plsar.annotations.Controller;
import net.plsar.annotations.Bind;
import net.plsar.annotations.http.Get;
import net.plsar.annotations.http.Post;
import net.plsar.model.PageCache;
import net.plsar.model.NetworkRequest;
import net.plsar.security.SecurityManager;

import java.io.IOException;

@Controller
public class ItemRouter {

    @Bind
    ItemService itemService;


    @Get("/query/{businessId}")
    public String query(PageCache cache,
                        NetworkRequest req,
                        SecurityManager security,
                        @Component Long id){
        return itemService.query(id, cache, req, security);
    }


    @Get("/{business}/items/{id}")
    public String getItem(PageCache cache,
                          NetworkRequest req,
                          SecurityManager security,
                          @Component String business,
                          @Component Long id){
        return itemService.getItem(id, business, cache, req, security);
    }

    @Get("/{business}/items/{categoryId}/{id}")
    public String getItem(PageCache cache,
                          NetworkRequest req,
                          SecurityManager security,
                          @Component String business,
                          @Component Long categoryId,
                          @Component Long id){
        return itemService.getItemCategory(id, categoryId, business, cache, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/items/new/{businessId}")
    public String configure(PageCache cache,
                            NetworkRequest req,
                            SecurityManager security,
                            @Component Long businessId){
        return itemService.create(businessId, cache, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/items/{businessId}")
    public String list(PageCache cache,
                       NetworkRequest req,
                       SecurityManager security,
                       @Component Long businessId){
        return itemService.list(businessId, cache, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/items/inactive/{businessId}")
    public String getListInactive(PageCache cache,
                                  NetworkRequest req,
                                  SecurityManager security,
                                  @Component Long businessId){
        return itemService.getListInactive(businessId, cache, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/items/grid/{businessId}")
    public String grid(PageCache cache,
                       NetworkRequest req,
                       SecurityManager security,
                       @Component Long businessId){
        return itemService.grid(businessId, cache, req, security);
    }

    @Post("/items/save/{businessId}")
    public String save(PageCache cache,
                       NetworkRequest req,
                       SecurityManager security,
                       @Component Long businessId) throws IOException {
        return itemService.save(businessId, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/items/edit/{businessId}/{id}")
    public String showcase(PageCache cache,
                           NetworkRequest req,
                           SecurityManager security,
                           @Component Long businessId,
                           @Component Long id) {
        return itemService.edit(id, businessId, cache, req, security);
    }

    @Post("/items/update/{businessId}/{id}")
    public String update(PageCache cache,
                         NetworkRequest req,
                         SecurityManager security,
                         @Component Long businessId,
                         @Component Long id) throws IOException {
        return itemService.update(id, businessId, false, cache, req, security);
    }

    @Post("/items/grid/update/{businessId}/{id}")
    public String gridUpdate(PageCache cache,
                             NetworkRequest req,
                             SecurityManager security,
                             @Component Long businessId,
                             @Component Long id) throws IOException {
        return itemService.update(id, businessId, true, cache, req, security);
    }

    @Post("/items/delete/{businessId}/{id}")
    public String delete(PageCache cache,
                         NetworkRequest req,
                         SecurityManager security,
                         @Component Long businessId,
                         @Component Long id){
        return itemService.delete(id, businessId, cache, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/items/options/{businessId}/{id}")
    public String options(PageCache cache,
                          NetworkRequest req,
                          SecurityManager security,
                          @Component Long businessId,
                          @Component Long id) throws Exception {
        return itemService.options(id, businessId, cache, req, security);
    }

    @Get("/items/options/save/{businessId}/{id}")
    public String getOptions(PageCache cache,
                             NetworkRequest req,
                             SecurityManager security,
                             @Component Long businessId,
                             @Component Long id) {
        return itemService.options(id, businessId, cache, req, security);
    }

    @Post("/items/options/save/{businessId}/{id}")
    public String saveOption(PageCache cache,
                             NetworkRequest req,
                             SecurityManager security,
                             @Component Long businessId,
                             @Component Long id){
        return itemService.saveOption(id, businessId, cache, req, security);
    }

    @Post("/items/options/delete/{businessId}/{optionId}/{id}")
    public String deleteOption(PageCache cache,
                               NetworkRequest req,
                               SecurityManager security,
                               @Component Long businessId,
                               @Component Long optionId,
                               @Component Long id){
        return itemService.deleteOption(id, optionId, businessId, cache, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/items/options/delete/{businessId}/{optionId}/{id}")
    public String deleteOption(PageCache cache,
                               NetworkRequest req,
                               SecurityManager security,
                               @Component Long businessId,
                               @Component Long id){
        return itemService.options(id, businessId, cache, req, security);
    }

    @Post("/items/options/values/save/{businessId}/{id}")
    public String saveValue(PageCache cache,
                            NetworkRequest req,
                            SecurityManager security,
                            @Component Long businessId,
                            @Component Long id){
        return itemService.saveValue(id, businessId, cache, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/items/options/values/save/{businessId}/{id}")
    public String getValues(PageCache cache,
                            NetworkRequest req,
                            SecurityManager security,
                            @Component Long businessId,
                            @Component Long id){
        return itemService.options(id, businessId, cache, req, security);
    }

    @Post("/items/options/values/delete/{businessId}/{valueId}/{id}")
    public String deleteValue(PageCache cache,
                              NetworkRequest req,
                              SecurityManager security,
                              @Component Long businessId,
                              @Component Long valueId,
                              @Component Long id){
        return itemService.deleteValue(id, valueId, businessId, cache, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/items/options/values/delete/{businessId}/{valueId}/{id}")
    public String deleteValue(PageCache cache,
                              NetworkRequest req,
                              SecurityManager security,
                              @Component Long businessId,
                              @Component Long id){
        return itemService.options(id, businessId, cache, req, security);
    }

}
