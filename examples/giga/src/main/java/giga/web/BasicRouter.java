package giga.web;

import giga.model.Business;
import giga.model.User;
import giga.model.UserBusiness;
import giga.repo.BusinessRepo;
import giga.repo.UserRepo;
import net.plsar.annotations.Design;
import net.plsar.annotations.Controller;
import net.plsar.annotations.Bind;
import net.plsar.annotations.network.Get;
import net.plsar.model.PageCache;
import net.plsar.model.NetworkRequest;
import net.plsar.security.SecurityManager;

import java.util.List;

@Controller
public class BasicRouter {

	@Bind
	UserRepo userRepo;

	@Bind
	BusinessRepo businessRepo;

	@Get("/")
	public String index(NetworkRequest req, SecurityManager security){
		if(security.isAuthenticated(req)){

			String credential = security.getUser(req);
			User authUser = userRepo.getPhone(credential);
			if(authUser == null){
				authUser = userRepo.getEmail(credential);
			}

			List<UserBusiness> userBusinesses = businessRepo.getListUsers(authUser.getId());
			if (userBusinesses.size() > 0) {
				Business business = null;
				for (UserBusiness userBusiness : userBusinesses) {
					business = businessRepo.get(userBusiness.getBusinessId());
					break;
				}
				return "redirect:/snapshot/" + business.getId();
			} else {
				return "redirect:/businesses/setup";
			}
		}
		return "redirect:/home";
	}

	
	@Design("/designs/guest.jsp")
	@Get("/home")
	public String home(PageCache cache){
		List<Business> businesses = businessRepo.getList();
		cache.set("businesses", businesses);
		cache.set("title", "A Marketplace for Marketplaces.");
		return "/pages/index.jsp";
	}

}