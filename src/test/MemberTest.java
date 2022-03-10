import com.shop.myapp.dto.Member;
import com.shop.myapp.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import static org.junit.Assert.assertEquals;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class MemberTest {

    @Autowired
    private MemberService memberService;
    @Test
    public void getMember(){
        int count = 0;
        for (int i=0; i<5; i++){
            Member member = new Member();
            member.setMemberId("test"+i);
            member.setMemberPwd("test"+i);
            member.setMemberName("test"+i);
            member.setMemberAddress("test"+i);
            member.setMemberTel("010-1111-1111");
            member.setMemberBirth(LocalDate.now());
            int result = memberService.insertMember(member);
            count += result;
        }
        assertEquals(count,5);
    }
    
    @Test
    public void updateMeber() {
    	 Member member = new Member();
         member.setMemberId("test");
         member.setMemberPwd("test");
         member.setMemberName("test");
         member.setMemberAddress("test");
         member.setMemberTel("010-1111-1111");
         member.setMemberBirth(LocalDate.now());
         int result = memberService.updateMember(member);
         
         assertEquals(result, 1);
    }
}
