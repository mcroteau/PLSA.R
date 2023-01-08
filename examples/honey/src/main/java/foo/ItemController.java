package foo;

import foo.model.Item;
import foo.repo.ItemRepo;
import net.plsar.annotations.Bind;
import net.plsar.annotations.Controller;
import net.plsar.annotations.Design;
import net.plsar.annotations.network.Get;
import net.plsar.annotations.network.Post;
import net.plsar.model.NetworkRequest;
import net.plsar.model.PageCache;

import java.util.List;

@Controller
public class ItemController {

    @Bind
    ItemRepo itemRepo;

    @Design("default.jsp")
    @Get("/")
    public String index(PageCache cache){
        List<Item> items = itemRepo.getList();
        cache.set("items", items);
        return "index.jsp";
    }

    @Post("/save")
    public String save(PageCache cache, NetworkRequest req){
        Item item = (Item) req.inflect(Item.class);
        itemRepo.save(item);
        cache.set("message", "Successfully added item.");
        return "redirect:/";
    }
}
