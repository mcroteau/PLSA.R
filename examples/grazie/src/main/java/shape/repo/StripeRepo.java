package shape.repo;

import net.plsar.Dao;
import net.plsar.annotations.Repository;
import shape.model.DynamicsPrice;
import shape.model.DynamicsProduct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StripeRepo {

    Dao dao;

    public StripeRepo(Dao dao){
        this.dao = dao;
    }

    public long getId() {
        String sql = "select max(id) from prices";
        Long id = dao.getLong(sql, new Object[]{});
        return id;
    }

    public long getProductId() {
        String sql = "select max(id) from products";
        Long id = dao.getLong(sql, new Object[]{});
        return id;
    }

    public long getPriceId() {
        String sql = "select max(id) from prices";
        Long id = dao.getLong(sql, new Object[]{});
        return id;
    }


    public Integer getCount() {
        String sql = "select count(*) from prices";
        Integer count = dao.getInt(sql, new Object[]{});
        return count;
    }

    public DynamicsProduct getProduct(long id){
        String sql = "select * from products where id = [+]";
        DynamicsProduct dynamicsProduct = (DynamicsProduct) dao.get(sql, new Object[] { id }, DynamicsProduct.class);
        return dynamicsProduct;
    }

    public DynamicsPrice getPrice(long id){
        String sql = "select * from prices where id = [+]";
        DynamicsPrice dynamicsPrice = (DynamicsPrice) dao.get(sql, new Object[] { id }, DynamicsPrice.class);
        return dynamicsPrice;
    }

    public DynamicsPrice getPriceAmount(BigDecimal amount){
        try {
            String sql = "select * from prices where amount = [+]";
            DynamicsPrice dynamicsPrice = (DynamicsPrice) dao.get(sql, new Object[]{amount}, DynamicsPrice.class);
            return dynamicsPrice;
        }catch(Exception e){}
        return null;
    }

    public List<DynamicsPrice> getList(){
        String sql = "select * from prices";
        List<DynamicsPrice> dynamicsPrices = (ArrayList) dao.getList(sql, new Object[]{}, DynamicsPrice.class);
        return dynamicsPrices;
    }

    public DynamicsProduct saveProduct(DynamicsProduct dynamicsProduct){
        System.out.println("stripe repo : " + dynamicsProduct.getNickname() + " :: " + dynamicsProduct.getStripeId());
        String sql = "insert into products (nickname, stripe_id) values ('[+]', '[+]')";
        dao.save(sql, new Object[] {
                dynamicsProduct.getNickname(),
                dynamicsProduct.getStripeId()
        });
        Long id = getProductId();
        DynamicsProduct savedProduct = getProduct(id);
        return savedProduct;
    }

    public DynamicsPrice savePrice(DynamicsPrice dynamicsPrice){
        String sql = "insert into prices (amount, nickname, product_id, stripe_id) values ([+], '[+]', [+], '[+]')";
        dao.save(sql, new Object[] {
                dynamicsPrice.getAmount(),
                dynamicsPrice.getNickname(),
                dynamicsPrice.getProductId(),
                dynamicsPrice.getStripeId()
        });
        Long id = getPriceId();
        DynamicsPrice savedPrice = getPrice(id);
        return savedPrice;
    }

    public boolean deleteProduct(long id){
        String sql = "delete from products where id = [+]";
        dao.delete(sql, new Object[] { id });
        return true;
    }

    public boolean deletePrice(long id){
        String sql = "delete from prices where id = [+]";
        dao.delete(sql, new Object[] { id });
        return true;
    }

    public DynamicsPrice getPriceProductId(Long id) {
        try{
            String sql = "select * from prices where product_id = [+]";
            DynamicsPrice dynamicsPrice = (DynamicsPrice) dao.get(sql, new Object[] { id }, DynamicsPrice.class);
            return dynamicsPrice;
        }catch(Exception ex){ }
        return null;
    }


    public boolean updatePrice(DynamicsPrice dynamicsPrice) {
        String sql = "update prices set stripe_id = '[+]' where id = [+]";
        dao.update(sql, new Object[] {
                dynamicsPrice.getStripeId(), dynamicsPrice.getId()
        });
        return true;
    }


    public boolean updateProduct(DynamicsProduct dynamicsProduct) {
        String sql = "update products set stripe_id = '[+]' where id = [+]";
        dao.update(sql, new Object[] {
                dynamicsProduct.getStripeId(), dynamicsProduct.getId()
        });
        return true;
    }

    public DynamicsProduct getProductStripeId(String stripeId) {
        try {
            String sql = "select * from products where stripe_id = '[+]'";
            DynamicsProduct dynamicsProduct = (DynamicsProduct) dao.get(sql, new Object[]{stripeId}, DynamicsProduct.class);
            return dynamicsProduct;
        }catch(Exception ex){ }
        return null;
    }

}
