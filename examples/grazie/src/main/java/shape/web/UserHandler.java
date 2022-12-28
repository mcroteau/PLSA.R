package shape.web;

import net.plsar.RouteAttributes;
import net.plsar.annotations.Bind;
import net.plsar.annotations.Component;
import net.plsar.annotations.Controller;
import net.plsar.annotations.Design;
import net.plsar.annotations.http.Get;
import net.plsar.annotations.http.Post;
import net.plsar.model.FileComponent;
import net.plsar.model.NetworkRequest;
import net.plsar.model.PageCache;
import net.plsar.model.RequestComponent;
import net.plsar.security.SecurityManager;
import shape.Underscore;
import shape.model.User;
import shape.repo.UserRepo;
import shape.service.SeaService;
import shape.service.SmsService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@Controller
public class UserHandler {

	public UserHandler(){
		this.smsService = new SmsService();
		this.seaService = new SeaService();
		this.underscore = new Underscore();
	}

	SmsService smsService;
	SeaService seaService;
	Underscore underscore;

	@Bind
	UserRepo userRepo;

	String getPermission(String id){
		return underscore.getUserMaintenance() + id;
	}

	@Get("/users")
	public String getUsers(NetworkRequest req, SecurityManager security, PageCache cache){
		if(!security.isAuthenticated(req)){
			return "redirect:/";
		}
		if(!security.hasRole(underscore.getSuperRole(), req)){
			cache.set("message", "You must be a super user in order to access users.");
			return "redirect:/";
		}

		List<User> users = userRepo.getList();
		cache.set("usersHref", "active");

		cache.set("users", users);
		cache.set("title", "Users");
		cache.set("page", "/pages/user/index.jsp");
		return "/designs/auth.jsp";
	}

	@Design("/designs/auth.jsp")
	@Get("/users/create")
	public String create(NetworkRequest req, SecurityManager security, PageCache cache){
		if (!security.isAuthenticated(req)) {
			return "redirect:/";
		}
		if (!security.hasRole(underscore.getSuperRole(), req)) {
			cache.set("message", "You must be a super user in order to access users.");
			return "redirect:/";
		}

		cache.set("users", "active");
		cache.set("title", "Create User");
		return "/pages/user/create.jsp";
	}

	@Post("/users/save")
	public String save(NetworkRequest req,
					   SecurityManager security,
					   PageCache cache) {
		if(!security.isAuthenticated(req)){
			return "redirect:/";
		}
		if(!security.hasRole(underscore.getSuperRole(), req)){
			cache.set("message", "You must be a super user in order to access users.");
			return "redirect:/";
		}

		User user = (User) req.inflect(User.class);
		String phone = underscore.getPhone(user.getPhone());
		User storedUser = userRepo.getPhone(phone);
		if(storedUser != null){
			cache.set("message", "Someone already exists with the same phone number. Please try a different number.");
			return "redirect:/users/create";
		}

		user.setPhone(phone);
		String password = security.hash(user.getPassword());
		user.setPassword(password);
		userRepo.save(user);

		User savedUser = userRepo.getSaved();

		cache.set("message", "Successfully added user!");
		return "redirect:/users/edit/" + savedUser.getId();
	}

	@Get("/users/edit/{id}")
	public String getEditUser(NetworkRequest req,
							  SecurityManager security,
							  PageCache cache,
							  @Component Long id){
		String permission = getPermission(Long.toString(id));
		if(!security.hasRole(underscore.getSuperRole(), req) &&
				!security.hasPermission(permission, req)){
			return "redirect:/";
		}

		User user = userRepo.get(id);
		if(user.getName().equals("null"))user.setName("");
		if(user.getPhone().equals("null"))user.setPhone("");

		cache.set("user", user);

		cache.set("page", "/pages/user/edit.jsp");
		return "/designs/guest.jsp";
	}

	@Post("/users/delete/{id}")
	public String deleteUser(NetworkRequest req,
							 SecurityManager security,
							 PageCache cache,
							 @Component Long id) {
		if(!security.hasRole(underscore.getSuperRole(), req)){
			cache.set("message", "You don't have permission");
			return "redirect:/";
		}

		cache.set("message", "Successfully deleted user");
		return "redirect:/admin/users";
	}

	@Post("/users/update/{id}")
	public String updateUser(NetworkRequest req,
							 SecurityManager security,
							 PageCache cache,
							 @Component Long id){
		if(!security.isAuthenticated(req)){
			return "redirect:/";
		}

		String permission = getPermission(Long.toString(id));
		if(!security.hasRole(underscore.getSuperRole(), req) &&
				!security.hasPermission(permission, req)){
			return "redirect:/";
		}

		User user = userRepo.get(id);

		RequestComponent requestComponent = req.getRequestComponent("media");
		List<FileComponent> fileParts = requestComponent.getFileComponents();

		RouteAttributes routeAttributes = req.getRouteAttributes();
		String key = (String) routeAttributes.get("cloud.key");
		String secret = (String) routeAttributes.get("cloud.secret");

		for (FileComponent part : fileParts) {
			String original = part.getFileName();
			InputStream is = new ByteArrayInputStream(part.getFileBytes());
			String ext = underscore.getExt(original);
			String name = underscore.getString(16) + "." + ext;

			seaService.send(name, key, secret, is);
			user.setImageUri(underscore.getOceanEndpoint() + name);
		}

		String description = req.getValue("description");
		String phone = underscore.getPhone(req.getValue("phone"));
		String name = req.getValue("name");

		user.setDescription(description);
		user.setPhone(phone);
		user.setName(name);

		userRepo.update(user);

		cache.set("message", "Successfully updated user.");
		return "redirect:/users/edit/" + id;

	}


	@Post("/users/signup")
	public String signup(){
		return "/pages/signup.jsp";
	}

	@Get("/users/reset")
	public String reset(){
		return "/pages/user/reset.jsp";
	}

	@Post("/users/send")
	public String sendReset(NetworkRequest req,
							SecurityManager security,
							PageCache cache){
		try {
			String phone = underscore.getPhone(req.getValue("phone"));
			User user = userRepo.getPhone(phone);
			if (user == null) {
				cache.set("message", "We were unable to find user with given cell phone number.");
				return ("redirect:/user/reset");
			}

			RouteAttributes routeAttributes = req.getRouteAttributes();
			String key = (String) routeAttributes.get("sms.key");

			String guid = underscore.getString(6);
			user.setPassword(security.hash(guid));
			userRepo.update(user);

			String message = "_ >_ Your temporary password is " + guid;
			smsService.send(user.getPhone(), message, key);

		}catch(Exception e){
			e.printStackTrace();
			cache.set("message", "Something went awry! You might need to contact support!");
			return "redirect:/signin";
		}

		cache.set("message", "Successfully sent reset password!");
		return "redirect:/signin";
	}

	@Design("/designs/guest.jsp")
	@Post("/users/reset/{id}")
	public String resetPassword(NetworkRequest req,
								SecurityManager security,
								PageCache cache,
								@Component Long id){

		User user = userRepo.get(id);
		String uuid = req.getValue("uuid");
		String username = req.getValue("username");
		String rawPassword = req.getValue("password");

		if(rawPassword.length() < 7){
			cache.set("message", "Passwords must be at least 7 characters long.");
			return "redirect:/user/confirm?username=" + username + "&uuid=" + uuid;
		}

		if(!rawPassword.equals("")){
			String password = security.hash(rawPassword);
			user.setPassword(password);
			userRepo.updatePassword(user);
		}

		cache.set("message", "Password successfully updated");
		return "/pages/user/success.jsp";
	}

}