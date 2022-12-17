package giga.web;

import giga.service.CartService;
import net.plsar.annotations.Component;
import net.plsar.annotations.Controller;
import net.plsar.annotations.Bind;
import net.plsar.annotations.http.Get;
import net.plsar.annotations.http.Post;
import net.plsar.model.PageCache;
import net.plsar.model.NetworkRequest;
import net.plsar.security.SecurityManager;

@Controller
public class CartRouter {

    @Bind
    CartService cartService;

    @Get("/{business}/cart")
    public String view(PageCache cache,
                       NetworkRequest req,
                       SecurityManager security,
                       @Component String businessUri){
        return cartService.view(businessUri.toLowerCase(), cache, req, security);
    }

    @Get("/{business}/checkout")
    public String viewCheckout(PageCache cache,
                               NetworkRequest req,
                               SecurityManager security,
                               @Component String businessUri){
        return cartService.viewCheckout(businessUri.toLowerCase(), cache, req, security);
    }

    @Post("/{business}/cart/add/{id}")
    public String add(PageCache cache,
                      NetworkRequest req,
                      SecurityManager security,
                      @Component String businessUri,
                      @Component Long id){
        System.out.println("\n\n////////////////////////////////");
        return cartService.add(id, businessUri.toLowerCase(), cache, req, security);
    }

    @Post("/{business}/cart/minus/{id}")
    public String minus(PageCache cache,
                      @Component String businessUri,
                      @Component Long id){
        System.out.println("minus");
        return cartService.minus(id, businessUri.toLowerCase(), cache);
    }


}
