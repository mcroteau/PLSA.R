package shape.repo;

import net.plsar.Dao;
import net.plsar.annotations.Repository;
import shape.model.Town;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TownRepo {

    Dao dao;

    public TownRepo(Dao dao){
        this.dao = dao;
    }

    public long getId() {
        String sql = "select max(id) from towns";
        long id = dao.getLong(sql, new Object[]{});
        return id;
    }

    public long getCount() {
        String sql = "select count(*) from towns";
        Long count = dao.getLong(sql, new Object[] { });
        return count;
    }

    public Town getSaved() {
        String idSql = "select max(id) from towns";
        long id = dao.getLong(idSql, new Object[]{});
        return get(id);
    }
    public Town get(String name){
        String sql = "select * from towns where name = '[+]'";
        Town status = (Town) dao.get(sql, new Object[]{ name }, Town.class);
        return status;
    }
    public Town get(long id){
        String sql = "select * from towns where id = [+]";
        Town status = (Town) dao.get(sql, new Object[]{ id }, Town.class);
        return status;
    }

    public List<Town> getList(){
        String sql = "select * from towns";
        List<Town> statuses = (ArrayList) dao.getList(sql, new Object[]{}, Town.class);
        return statuses;
    }

    public boolean save(Town town){
        String sql = "insert into towns (name) " +
                "values ('[+]')";
        dao.update(sql, new Object[] {
                town.getName()
        });
        return true;
    }

    public boolean update(Town town) {
        String sql = "update towns set latitude = '[+]', longitude = '[+]' where id = [+]";
        dao.update(sql, new Object[] {
                town.getLatitude(),
                town.getLongitude(),
                town.getId()
        });
        return true;
    }

    public boolean delete(long id){
        String sql = "delete from towns where id = [+]";
        dao.delete(sql, new Object[] { id });
        return true;
    }

}
