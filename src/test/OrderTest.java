import com.shop.myapp.dto.Order;
import com.shop.myapp.dto.OrderDetail;
import com.shop.myapp.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Transactional
public class OrderTest {
    @Autowired
    private OrderService orderService;
    private String getOrderCode;

    @Test
    public void getOrderCode() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String format = now.format(formatter);
        System.out.println(format);
        this.getOrderCode = format;
    }

    @Test
    public void insertOrderAndOrderDetails() throws Exception {
        Order order = Order
                .builder()
                .orderCode(getOrderCode)
                .memberId("test2")
                .buyerAddr("home")
                .buyerName("test2")
                .buyerEmail("test@naver.com")
                .buyerPostCode("1111")
                .isPaid("NotPaid")
                .build();
        List<String> cartIds = Arrays.asList("45","46");
        Order order1 = orderService.insertOrder(order, cartIds);
    }

    @Test
    public void findByOrderCode(){
        Order byOrderCode = orderService.findByOrderCode("20220220220206");
        System.out.println(byOrderCode.getOrderCode());
        System.out.println(byOrderCode.getTotalPay());
    }
}
