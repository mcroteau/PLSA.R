package shape.repo;

import net.plsar.Dao;
import net.plsar.annotations.Repository;
import shape.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserRepo {

	Dao dao;

	public UserRepo(Dao dao){
		this.dao = dao;
	}

	public UserBusiness getSavedBusiness() {
		String idSql = "select max(id) from user_businesses";
		long id = dao.getLong(idSql, new Object[]{});
		return getBusiness(id);
	}

	public User getSaved() {
		String idSql = "select max(id) from users";
		long id = dao.getLong(idSql, new Object[]{});
		return get(id);
	}

	public long getId() {
		String sql = "select max(id) from users";
		long id = dao.getLong(sql, new Object[]{});
		return id;
	}

	public long getCount() {
		String sql = "select count(*) from users";
		Long count = dao.getLong(sql, new Object[] { });
		return count;
	}

	public User get(long id) {
		String sql = "select * from users where id = [+]";
		User user = (User) dao.get(sql, new Object[] { id }, User.class);
		return user;
	}

	public User get(String email) {
		String sql = "select * from users where email = '[+]'";
		User user = (User) dao.get(sql, new Object[] { email }, User.class);
		return user;
	}

	public User getPhone(String username) {
		String sql = "select * from users where phone = '[+]'";
		User user = (User) dao.get(sql, new Object[] { username }, User.class);
		return user;
	}

	public List<User> getList() {
		String sql = "select * from users";
		List<User> users = (ArrayList) dao.getList(sql, new Object[]{}, User.class);
		return users;
	}

	public Boolean save(User user) {
		String sql = "insert into users (uuid, guid, name, phone, email, password, clean, date_created) " +
				"values ('[+]','[+]','[+]','[+]','[+]','[+]','[+]',[+])";
		dao.save(sql, new Object[]{
				user.getUuid(),
				user.getGuid(),
				user.getName(),
				user.getPhone(),
				user.getEmail(),
				user.getPassword(),
				user.getClean(),
				user.getDateCreated()
		});
		return true;
	}

	public Boolean saveBusiness(UserBusiness userBusiness) {
		String sql = "insert into user_businesses (user_id, business_id) " +
				"values ([+],[+])";
		dao.save(sql, new Object[]{
				userBusiness.getUserId(),
				userBusiness.getBusinessId()
		});
		return true;
	}

	public boolean update(User user) {
		String sql = "update users set name = '[+]', phone = '[+]', image_uri = '[+]', " +
				"stripe_account_id = '[+]', description = '[+]', activated = [+] where id = [+]";
		dao.update(sql, new Object[]{
				user.getName(),
				user.getPhone(),
				user.getImageUri(),
				user.getStripeAccountId(),
				user.getDescription(),
				user.isActivated(),
				user.getId()
		});
		return true;
	}

	public boolean updateBusiness(UserBusiness userBusiness) {
		String sql = "update user_businesses set active = [+], years = [+], date_started = [+], " +
				"position = '[+]', part_time = [+] where id = [+]";
		dao.update(sql, new Object[]{
				userBusiness.isActive(),
				userBusiness.getYears(),
				userBusiness.getDateStarted(),
				userBusiness.getPosition(),
				userBusiness.isPartTime(),
				userBusiness.getId()
		});
		return true;
	}

	public boolean updatePassword(User user) {
		String sql = "update users set password = '[+]' where id = [+]";
		dao.update(sql, new Object[]{
				user.getPassword(),
				user.getId()
		});
		return true;
	}

	public User getReset(String username, String uuid){
		String sql = "select * from users where username = '[+]' and uuid = '[+]'";
		User user = (User) dao.get(sql, new Object[] { username, uuid }, User.class);
		return user;
	}

	public boolean delete(long id) {
		String sql = "delete from users where id = [+]";
		dao.update(sql, new Object[] { id });
		return true;
	}

	public String getUserPassword(String phone) {
		User user = getPhone(phone);
		return user.getPassword();
	}

	public boolean saveUserRole(long userId, long roleId){
		String sql = "insert into user_roles (role_id, user_id) values ([+], [+])";
		dao.save(sql, new Object[]{roleId, userId});
		return true;
	}

	public boolean saveUserRole(long userId, String roleName){
		Role role = (Role) dao.get("select * from roles where name = '[+]'", new Object[]{roleName}, Role.class);
		String sql = "insert into user_roles (role_id, user_id) values ([+], [+])";
		dao.save(sql, new Object[]{role.getId(), userId});
		return true;
	}

	public boolean savePermission(long accountId, String permission){
		String sql = "insert into user_permissions (user_id, permission) values ([+], '[+]')";
		dao.save(sql, new Object[]{ accountId,  permission });
		return true;
	}

	public Set<String> getUserRoles(long id) {
		String sql = "select r.name as name from user_roles ur inner join roles r on r.id = ur.role_id where ur.user_id = [+]";
		List<UserRole> rolesList = (ArrayList) dao.getList(sql, new Object[]{ id }, UserRole.class);
		Set<String> roles = new HashSet<>();
		for(UserRole role: rolesList){
			roles.add(role.getName());
		}
		return roles;
	}

	public Set<String> getUserPermissions(long id) {
		String sql = "select permission from user_permissions where user_id = [+]";
		List<UserPermission> permissionsList = (ArrayList) dao.getList(sql, new Object[]{ id }, UserPermission.class);
		Set<String> permissions = new HashSet<>();
		for(UserPermission permission: permissionsList){
			permissions.add(permission.getPermission());
		}
		return permissions;
	}

	public User getEmail(String email) {
		String sql = "select * from users where email = '[+]'";
		User user = (User) dao.get(sql, new Object[] { email }, User.class);
		return user;
	}

	public List<UserBusiness> getBusinesses(Long id) {
		String sql = "select * from user_businesses where user_id = [+] and active = true";
		List<UserBusiness> userBusinesses = (ArrayList) dao.getList(sql, new Object[]{ id }, UserBusiness.class);
		return userBusinesses;
	}

	public List<UserBusiness> getUsers(Long id) {
		String sql = "select * from user_businesses where business_id = [+]";
		List<UserBusiness> userBusinesses = (ArrayList) dao.getList(sql, new Object[]{ id }, UserBusiness.class);
		return userBusinesses;
	}

	public UserBusiness getBusiness(Long id) {
		String sql = "select * from user_businesses where id = [+]";
		UserBusiness userBusiness = (UserBusiness) dao.get(sql, new Object[] { id }, UserBusiness.class);
		return userBusiness;
	}

	public User getUuid(String uuid) {
		String sql = "select * from users where uuid = '[+]'";
		User user = (User) dao.get(sql, new Object[] { uuid }, User.class);
		return user;
	}

	public User getGuid(String guid) {
		String sql = "select * from users where guid = '[+]'";
		User user = (User) dao.get(sql, new Object[] { guid }, User.class);
		return user;
	}
}