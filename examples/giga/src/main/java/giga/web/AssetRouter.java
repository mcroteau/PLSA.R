package giga.web;

import giga.service.AssetService;
import net.plsar.annotations.*;
import net.plsar.annotations.network.Get;
import net.plsar.annotations.network.Post;
import net.plsar.model.PageCache;
import net.plsar.model.NetworkRequest;
import net.plsar.model.NetworkResponse;
import net.plsar.security.SecurityManager;

@Controller
public class AssetRouter {

    @Bind
    AssetService assetService;

    @Text
    @Get("/go/{n}")
    public String getAsset(NetworkResponse resp,
                           @Component String meta) throws Exception {
        return assetService.getAsset(meta, resp);
    }

    @Media
    @Get("/media/{n}")
    public String getMedia(NetworkResponse resp,
                           @Component String meta) throws Exception {
        return assetService.getMedia(meta, resp);
    }

    @Design("/designs/auth.jsp")
    @Get("/assets/new/{businessId}")
    public String configure(PageCache cache,
                            @Component Long businessId,
                            NetworkRequest req,
                            SecurityManager security){
        return assetService.create(businessId, cache, req, security);
    }

    @Design("/designs/auth.jsp")
    @Get("/assets/{businessId}")
    public String list(PageCache cache,
                       @Component Long businessId,
                       NetworkRequest req,
                       SecurityManager security){
        return assetService.list(businessId, cache, req, security);
    }

    @Post("/assets/save")
    public String save(NetworkRequest req,
                       SecurityManager security) throws Exception {
        return assetService.save(req, security);
    }

    @Post("/assets/delete/{businessId}/{id}")
    public String delete(PageCache cache,
                         @Component Long businessId,
                         @Component Long id,
                         NetworkRequest req,
                         SecurityManager security){
        return assetService.delete(id, businessId, cache, req, security);
    }
}
