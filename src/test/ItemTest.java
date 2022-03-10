import com.shop.myapp.dto.Item;
import com.shop.myapp.dto.ItemOption;
import com.shop.myapp.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class ItemTest {

    @Autowired
    private ItemService itemService;

    @Test
    public void insertItems() {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            Item item = new Item();

            List<ItemOption> itemOptions = new ArrayList<>();
            for (int j = 0; j < 5; j++) {

                ItemOption itemOption = new ItemOption();
                itemOption.setOptionName(Integer.toString(200 + 5 * j));
                itemOption.setOptionStock((int) (Math.random() * 10) + 1);
                itemOption.setOptionPriceUd(0);
                itemOptions.add(itemOption);
            }

            item.setItemName("testItem" + i);
            item.setItemPrice((int) (Math.random() * 10000) + 1000);
            item.setCountryCode("410");
            item.setMemberId("test1");
            item.setItemInfo("test Item" + i);
            item.setItemImage("없음");
            item.setItemOptions(itemOptions);
            int result = itemService.createItem(item);
            count += result;
        }
        assertEquals(count, 8*5);
    }

    @Test
    public void findByItem() {
        Item item = itemService.getItem("7");
        item.calculateItemStock();
        System.out.println("ItemName : " + item.getItemName());
        System.out.println(item.getItemPrice());
        int stock = 0;
        for (ItemOption itemOption : item.getItemOptions()) {
            System.out.println("OptionCode : " + itemOption.getItemCode());
            System.out.println("OptionName : " + itemOption.getOptionName());
            System.out.println("itemOption.getOptionStock() = " + itemOption.getOptionStock());
            stock += itemOption.getOptionStock();
        }
        System.out.println("itemStock : " + item.getItemStock());
        assertEquals(stock, item.getItemStock());
    }
}
