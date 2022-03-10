import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class DateTest {
    @Test
    public void dateTest(){
        long sec = 1645411469L*1000L;
        LocalDateTime paidDate =  Instant.ofEpochMilli(sec)
                .atZone(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();
        System.out.println(paidDate);

    }
    @Test
    public void parseDate(){
        String paidAt = "2022-02-21T13:34:07";
        LocalDateTime d = LocalDateTime.parse(paidAt);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("d = " + d.format(formatter));
    }
}
