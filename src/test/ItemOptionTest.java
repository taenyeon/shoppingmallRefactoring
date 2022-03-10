import com.shop.myapp.dto.ItemOption;
import com.shop.myapp.service.ItemOptionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class ItemOptionTest {

    @Autowired
    private ItemOptionService itemOptionService;

    @Test
    public void getItemOption(){
        Optional<ItemOption> itemOptionOptional = itemOptionService.findByItemCode("7");
        ItemOption itemOption = itemOptionOptional.orElseThrow(() -> new IllegalStateException("아이템 없음"));
        System.out.println("itemOption.getOptionName() = " + itemOption.getOptionName());
        System.out.println("itemOption.getItem().getItemName() = " + itemOption.getItem().getItemName());

    }
}
