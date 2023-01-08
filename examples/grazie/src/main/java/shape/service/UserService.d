package shape.service;

import net.plsar.annotations.Bind;
import net.plsar.annotations.Service;
import net.plsar.model.NetworkRequest;
import net.plsar.model.PageCache;
import shape.Grazie;
import shape.model.User;
import shape.repo.UserRepo;
import qio.Qio;
import qio.annotate.Inject;
import qio.annotate.Service;
import qio.model.web.PageCache;
import perched.Parakeet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Bind
    UserRepo userRepo;

    @Bind
    SeaService seaService;

    @Bind
    SmsService smsService;


    private String getPermission(String id){
        return Underscore.USER_MAINTENANCE + id;
    }

//    public String create(PageCache cache) {
//        if (!authService.isAuthenticated()) {
//            return "redirect:/";
//        }
//        if (!authService.isAdministrator()) {
//            data.put("message", "You must be a super user in order to access users.");
//            return "redirect:/";
//        }
//
//        cache.set("users", "active");
//        cache.set("title", "Create User");
//        return "/pages/user/create.jsp";
//    }


    public String getUsers(PageCache data){
        if(!authService.isAuthenticated()){
            return "redirect:/";
        }
        if(!authService.isAdministrator()){
            data.put("message", "You must be a super user in order to access users.");
            return "redirect:/";
        }

        List<User> users = userRepo.getList();
        data.put("usersHref", "active");

        data.put("users", users);
        data.put("title", "Users");
        data.put("page", "/pages/user/index.jsp");
        return "/designs/auth.jsp";
    }

    public String save(PageCache data, NetworkRequest req){
        if(!authService.isAuthenticated()){
            return "redirect:/";
        }
        if(!authService.isAdministrator()){
            data.put("message", "You must be a super user in order to access users.");
            return "redirect:/";
        }

        User user = (User) Qio.hydrate(req, User.class);
        String phone = Underscore.getPhone(user.getPhone());
        User storedUser = userRepo.getPhone(phone);
        if(storedUser != null){
            data.set("message", "Someone already exists with the same phone number. Please try a different number.");
            return "redirect:/users/create";
        }

        user.setPhone(phone);
        String password = Parakeet.dirty(user.getPassword());
        user.setPassword(password);
        userRepo.save(user);

        User savedUser = userRepo.getSaved();

        data.set("message", "Successfully added user!");
        return "redirect:/users/edit/" + savedUser.getId();
    }



    public String getEditUser(Long id, PageCache data){
        String permission = getPermission(Long.toString(id));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "redirect:/";
        }

        User user = userRepo.get(id);
        if(user.getName().equals("null"))user.setName("");
        if(user.getPhone().equals("null"))user.setPhone("");

        data.put("user", user);

        data.put("page", "/pages/user/edit.jsp");
        return "/designs/guest.jsp";
    }


    public String editPassword(Long id, PageCache data) {
        String permission = getPermission(Long.toString(id));
        if(!authService.isAdministrator() ||
                !authService.hasPermission(permission)){
            return "redirect:/";
        }

        User user = userRepo.get(id);
        data.put("user", user);

        data.put("page", "/pages/user/edit_password.jsp");
        return "/designs/auth.jsp";
    }

    public String updatePassword(User user, PageCache data) {

        String permission = getPermission(Long.toString(user.getId()));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "redirect:/";
        }

        if(user.getPassword().length() < 7){
            data.put("message", "Passwords must be at least 7 characters long.");
            return "redirect:/signup";
        }

        if(!user.getPassword().equals("")){
            user.setPassword(Parakeet.dirty(user.getPassword()));
            userRepo.updatePassword(user);
        }

        data.put("message", "password successfully updated");
        Long id = authService.getUser().getId();
        return "redirect:/user/edit_password/" + id;

    }

    public String deleteUser(Long id, PageCache data) {
        if(!authService.isAdministrator()){
            data.put("message", "You don't have permission");
            return "redirect:/";
        }

        data.put("message", "Successfully deleted user");
        return "redirect:/admin/users";
    }


    public String sendReset(PageCache data, NetworkRequest req) {

        try {
            String phone = Underscore.getPhone(req.getParameter("phone"));
            User user = userRepo.getPhone(phone);
            if (user == null) {
                data.put("message", "We were unable to find user with given cell phone number.");
                return ("redirect:/user/reset");
            }

            String guid = Underscore.getString(6);
            user.setPassword(Parakeet.dirty(guid));
            userRepo.update(user);

            String message = "_ >_ Your temporary password is " + guid;
            smsService.send(user.getPhone(), message);

        }catch(Exception e){
            e.printStackTrace();
            data.set("message", "Something went awry! You might need to contact support!");
            return "redirect:/signin";
        }

        data.set("message", "Successfully sent reset password!");
        return "redirect:/signin";
    }

    public String resetPassword(Long id, PageCache data, NetworkRequest req) {

        User user = userRepo.get(id);
        String uuid = req.getParameter("uuid");
        String username = req.getParameter("username");
        String rawPassword = req.getParameter("password");

        if(rawPassword.length() < 7){
            data.put("message", "Passwords must be at least 7 characters long.");
            return "redirect:/user/confirm?username=" + username + "&uuid=" + uuid;
        }

        if(!rawPassword.equals("")){
            String password = Parakeet.dirty(rawPassword);
            user.setPassword(password);
            userRepo.updatePassword(user);
        }

        data.put("message", "Password successfully updated");
        data.put("page", "/pages/user/success.jsp");
        return "/designs/guest.jsp";
    }

    public String updateUser(Long id, PageCache data, NetworkRequest req) throws IOException, ServletException {
        if(!authService.isAuthenticated()){
            return "redirect:/";
        }

        String permission = getPermission(Long.toString(id));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "redirect:/";
        }

        User user = userRepo.get(id);

        List<Part> fileParts = req.getParts()
                .stream()
                .filter(part -> "media".equals(part.getName()) && part.getSize() > 0)
                .collect(Collectors.toList());

        for (Part part : fileParts) {
            String original = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            InputStream is = part.getInputStream();
            String ext = Underscore.getExt(original);
            String name = Underscore.getString(16) + "." + ext;
            seaService.send(name, is);
            user.setImageUri(Underscore.OCEAN_ENDPOINT + name);
        }

        String description = req.getParameter("description");
        String phone = Underscore.getPhone(req.getParameter("phone"));
        String name = req.getParameter("name");

        user.setDescription(description);
        user.setPhone(phone);
        user.setName(name);

        userRepo.update(user);

        data.put("message", "Successfully updated user.");
        return "redirect:/users/edit/" + id;

    }

}
