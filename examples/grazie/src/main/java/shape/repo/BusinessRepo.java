package shape.repo;

import net.plsar.Dao;
import net.plsar.annotations.Repository;
import shape.model.Business;
import shape.model.SignUpRequest;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BusinessRepo {

    Dao dao;

    public BusinessRepo(Dao dao){
        this.dao = dao;
    }

    public long getId() {
        String sql = "select max(id) from businesses";
        long id = dao.getLong(sql, new Object[]{});
        return id;
    }

    public long getCount() {
        String sql = "select count(*) from businesses";
        Long count = dao.getLong(sql, new Object[] { });
        return count;
    }

    public Business get(long id){
        String sql = "select * from businesses where id = [+]";
        Business business = (Business) dao.get(sql, new Object[]{ id }, Business.class);
        return business;
    }

    public Business get(String name){
        String sql = "select * from businesses where name = '[+]'";
        Business business = (Business) dao.get(sql, new Object[]{ name }, Business.class);
        return business;
    }

    public Business getLatLng(String lat, String lng){
        String sql = "select * from businesses where latitude = '[+]' and longitude = '[+]'";
        Business business = (Business) dao.get(sql, new Object[]{ lat, lng }, Business.class);
        return business;
    }

    public List<Business> getList(){
        String sql = "select * from businesses";
        List<Business> businesses = (ArrayList) dao.getList(sql, new Object[]{}, Business.class);
        return businesses;
    }

    public List<Business> getList(Long id){
        String sql = "select * from businesses where town_id = [+] order by name asc";
        List<Business> businesses = (ArrayList) dao.getList(sql, new Object[]{ id }, Business.class);
        return businesses;
    }

    public boolean save(Business business){
        String sql = "insert into businesses (name, address, latitude, longitude, town_id) " +
                "values ('[+]','[+]','[+]','[+]',[+])";
        dao.update(sql, new Object[] {
                business.getName(),
                business.getAddress(),
                business.getLatitude(),
                business.getLongitude(),
                business.getTownId()
        });
        return true;
    }

    public boolean saveSignupRequest(SignUpRequest signUpRequest){
        String sql = "insert into signup_requests (name, address) " +
                "values ('[+]','[+]')";
        dao.update(sql, new Object[] {
                signUpRequest.getName(),
                signUpRequest.getAddress()
        });
        return true;
    }

    public boolean update(Business business) {
        String sql = "update businesses set active = [+] where id = [+]";
        dao.update(sql, new Object[] {
                business.getId()
        });
        return true;
    }

    public List<SignUpRequest> getSignupRequests(){
        String sql = "select * from signup_requests order by id desc";
        List<SignUpRequest> signUpRequests = (ArrayList) dao.getList(sql, new Object[]{ }, SignUpRequest.class);
        return signUpRequests;
    }

    public boolean delete(long id){
        String sql = "delete from businesses where id = [+]";
        dao.delete(sql, new Object[] { id });
        return true;
    }

    public boolean deleteSignupRequest(long id){
        String sql = "delete from signup_requests where id = [+]";
        dao.delete(sql, new Object[] { id });
        return true;
    }

}
