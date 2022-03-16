import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class QnaTest {
    @Test
    public void dateTest(){
        long sec = 1645411469L*1000L;
        LocalDateTime paidDate =  Instant.ofEpochMilli(sec)
                .atZone(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();
        System.out.println(paidDate);
    }

    @Test
    public void arrCheck(){
        List<Integer> numList = new LinkedList<>();
        numList.add(1);
        numList.add(1);
        numList.add(2);
        numList.add(2);
        numList.add(3);
        for (int i = 0; i < numList.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (numList.get(i).equals(numList.get(j))) {

                    numList.remove(numList.get(j));
                }
            }
            System.out.println(numList.get(i));
        }
    }
}
