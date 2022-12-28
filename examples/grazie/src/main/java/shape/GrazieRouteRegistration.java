package shape;

import net.plsar.RouteAttributes;
import net.plsar.annotations.RouteRegistration;
import net.plsar.implement.RouteRegister;

@RouteRegistration
public class GrazieRouteRegistration implements RouteRegister {

    @Override
    public RouteAttributes register() {
        RouteAttributes routeAttributes = new RouteAttributes();
        routeAttributes.set("site.host", "localhost");
        routeAttributes.set("sms.key", "");
        routeAttributes.set("cloud.key", "");
        routeAttributes.set("cloud.secret", "");
        routeAttributes.set("stripe.key", "");
        return routeAttributes;
    }

}
