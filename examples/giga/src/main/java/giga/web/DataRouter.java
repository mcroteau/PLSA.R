package giga.web;

import giga.service.DataService;
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
public class DataRouter {

    @Bind
    DataService dataService;

    @Design("/designs/auth.jsp")
    @Get("/import/media/{businessId}")
    public String viewImport(PageCache cache,
                             NetworkRequest req,
                             SecurityManager security,
                             @Component Long businessId) {
        return dataService.viewImportMedia(businessId, cache, req, security);
    }

    @Post("/import/media/{businessId}")
    public String importMedia(PageCache cache,
                              NetworkRequest req,
                              SecurityManager security,
                              @Component Long businessId) throws Exception {
        return dataService.importMedia(businessId, cache, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/imports/media/{businessId}")
    public String viewImports(PageCache cache,
                              NetworkRequest req,
                              SecurityManager security,
                              @Component Long businessId) {
        return dataService.viewImportsMedia(businessId, cache, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/imports/media/{businessId}/{importId}")
    public String viewImports(PageCache cache,
                              NetworkRequest req,
                              SecurityManager security,
                              @Component Long businessId,
                              @Component Long importId) {
        return dataService.viewMedias(businessId, importId, cache, req, security);
    }

    @Post("/import/media/update/{businessId}/{importId}")
    public String importMedia(PageCache cache,
                              NetworkRequest req,
                              SecurityManager security,
                              @Component Long businessId,
                              @Component Long importId){
        return dataService.updateMedia(businessId, importId, cache, req, security);
    }


    @Post("/import/media/convert/{businessId}/{importId}")
    public String convertItems(PageCache cache,
                               NetworkRequest req,
                               SecurityManager security,
                               @Component Long businessId,
                               @Component Long importId){
        return dataService.convertItems(businessId, importId, cache, req, security);
    }

    @Post("/import/media/delete/{businessId}/{importId}")
    public String deleteImport(PageCache cache,
                               NetworkRequest req,
                               SecurityManager security,
                               @Component Long businessId,
                               @Component Long importId){
        return dataService.deleteImport(businessId, importId, cache, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/imports/item_groups/new/{businessId}")
    public String viewItemGroupImport(PageCache cache,
                                      NetworkRequest req,
                                      SecurityManager security,
                                      @Component Long businessId) {
        return dataService.viewItemGroupImport(businessId, cache, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/imports/item_groups/{businessId}")
    public String itemGroups(PageCache cache,
                             NetworkRequest req,
                             SecurityManager security,
                             @Component Long businessId) {
        return dataService.viewItemGroups(businessId, cache, req, security);
    }

    @Post("/imports/item_groups/{businessId}")
    public String ingest(PageCache cache,
                         NetworkRequest req,
                         SecurityManager security,
                         @Component Long businessId) {
        return dataService.ingest(businessId, cache, req, security);
    }

    @Post("/imports/item_groups/delete/{businessId}/{id}")
    public String deleteGroupImport(PageCache cache,
                                    NetworkRequest req,
                                    SecurityManager security,
                                    @Component Long businessId,
                                    @Component Long ingestId) {
        return dataService.deleteGroupImport(ingestId, businessId, cache, req, security);
    }

    @Post("/imports/item_groups/group/delete/{businessId}/{id}")
    public String deleteGroups(PageCache cache,
                               NetworkRequest req,
                               SecurityManager security,
                               @Component Long businessId,
                               @Component Long groupId) {
        return dataService.deleteImportedGroup(groupId, businessId, cache, req, security);
    }
}
