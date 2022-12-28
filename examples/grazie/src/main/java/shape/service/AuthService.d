package shape.service;

import net.plsar.model.NetworkRequest;
import net.plsar.model.NetworkResponse;
import net.plsar.security.SecurityManager;
import shape.Underscore;
import shape.model.User;
import shape.repo.UserRepo;
import perched.Parakeet;
import qio.annotate.Inject;
import qio.annotate.Service;
import qio.model.web.PageCache;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthService {

    @Bind
    UserRepo userRepo;

    public boolean signin(String phone, String password){
        User user = userRepo.getPhone(phone);
        if(user == null) {
            user = userRepo.getEmail(phone);
        }
        return Parakeet.login(phone, password);
    }

    public boolean signout(){
        return Parakeet.logout();
    }

    public boolean isAuthenticated(){
        return Parakeet.isAuthenticated();
    }

    public boolean isAdministrator(){
        return Parakeet.hasRole(Underscore.SUPER_ROLE);
    }

    public boolean hasPermission(String permission){
        return Parakeet.hasPermission(permission);
    }

    public boolean hasRole(String role){
        return Parakeet.hasRole(role);
    }

    public User getUser(NetworkRequest req, SecurityManager security){
        String credential = security.getUser(req);
        User user = userRepo.getPhone(credential);
        if(user == null){
            user = userRepo.getEmail(credential);
        }
        return user;
    }

    public String deAuthenticate(PageCache data, HttpServletRequest req) {
        signout();
        data.put("message", "Successfully signed out");
        req.getSession().setAttribute("username", "");
        req.getSession().setAttribute("userId", "");
        return "redirect:/";
    }
}
