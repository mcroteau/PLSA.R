package foo.repo;

import foo.model.Item;
import net.plsar.Dao;
import net.plsar.annotations.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepo {

    Dao dao;

    public ItemRepo(Dao dao) {
        this.dao = dao;
    }

    public long getId() {
        String sql = "select max(id) from items";
        long id = dao.getLong(sql, new Object[]{});
        return id;
    }

    public Long getCount() {
        String sql = "select count(*) from items";
        Long count = dao.getLong(sql, new Object[]{});
        return count;
    }

    public Item get(long id){
        String sql = "select * from items where id = [+]";
        Item paper = (Item) dao.get(sql, new Object[]{ id }, Item.class);
        return paper;
    }

    public Item get(long id, long userId){
        String sql = "select * from items where id = [+]";
        Item paper = (Item) dao.get(sql, new Object[]{ id, userId }, Item.class);
        return paper;
    }

    public List<Item> getList(){
        String sql = "select * from items";
        List<Item> papers = (ArrayList) dao.getList(sql, new Object[]{}, Item.class);
        return papers;
    }

    public void save(Item item){
        String sql = "insert into items (description) values ('[+]')";
        dao.save(sql, new Object[] {
            item.getDescription()
        });
    }

    public void update(Item item) {
        String sql = "update items set description = '[+]' where id = [+]";
        dao.update(sql, new Object[]{
            item.getDescription(),
            item.getId()
        });
    }

    public boolean delete(long id){
        String sql = "delete from items where id = [+]";
        dao.delete(sql, new Object[] { id });
        return true;
    }

}
