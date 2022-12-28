package shape.repo;

import net.plsar.Dao;
import net.plsar.annotations.Repository;
import shape.model.Tip;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TipRepo {

    Dao dao;

    public TipRepo(Dao dao){
        this.dao = dao;
    }

    public long getId() {
        String sql = "select max(id) from tips";
        long id = dao.getLong(sql, new Object[]{});
        return id;
    }

    public Tip getSaved() {
        String idSql = "select max(id) from tips";
        long id = dao.getLong(idSql, new Object[]{});
        return get(id);
    }

    public Long getCount() {
        String sql = "select count(*) from tips";
        Long count = dao.getLong(sql, new Object[]{});
        return count;
    }

    public Tip get(long id){
        String sql = "select * from tips where id = [+]";
        Tip donation = (Tip) dao.get(sql, new Object[] { id }, Tip.class);
        return donation;
    }

    public Tip get(String subscriptionId){
        String sql = "select * from tips where subscription_id = '[+]'";
        Tip donation = (Tip) dao.get(sql, new Object[] { subscriptionId }, Tip.class);
        return donation;
    }
    public Tip getGuid(String guid){
        String sql = "select * from tips where guid = '[+]'";
        Tip donation = (Tip) dao.get(sql, new Object[] { guid }, Tip.class);
        return donation;
    }

    public Tip save(Tip tip){
        String sql = "insert into tips " +
                "(guid, amount, amount_cents, charge_id, subscription_id, patron_id, recipient_id, tip_date, email, recurring) values " +
                "('[+]',[+],'[+]','[+]',[+],[+],[+],[+],'[+]',[+])";
        dao.save(sql, new Object[] {
                tip.getGuid(),
                tip.getAmount(),
                tip.getAmountCents(),
                tip.getChargeId(),
                tip.getSubscriptionId(),
                tip.getPatronId(),
                tip.getRecipientId(),
                tip.getTipDate(),
                tip.getEmail(),
                tip.isRecurring()
        });
        Long id = getId();
        Tip savedTip = get(id);
        return savedTip;
    }

    public boolean update(Tip donation) {
        System.out.println(
                donation.getProcessed() + " :: charge : " +
                        donation.getChargeId() + " :: subscription : " +
                        donation.getSubscriptionId() + " :: " +
                        donation.getId());

        String sql = "update tips set processed = [+], charge_id = '[+]', subscription_id = '[+]' where id = [+]";
        dao.update(sql, new Object[]{
                donation.getProcessed(),
                donation.getChargeId(),
                donation.getSubscriptionId(),
                donation.getId()
        });
        return true;
    }

    public List<Tip> getList(){
        String sql = "select * from tips";
        List<Tip> tips = (ArrayList) dao.getList(sql, new Object[]{}, Tip.class);
        return tips;
    }

    public List<Tip> getList(long userId) {
        String sql = "select * from tips where recipient_id = [+] and processed = true order by tip_date desc";
        List<Tip> tips = (ArrayList) dao.getList(sql, new Object[]{ userId }, Tip.class);
        return tips;
    }

}
