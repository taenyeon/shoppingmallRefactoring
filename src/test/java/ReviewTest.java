import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.shop.myapp.dto.Review;
import com.shop.myapp.service.ReviewService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Transactional

public class ReviewTest {
	
	/*@Autowired
	private ReviewService reviewService;
	
	@Test
	public void getReview() {
		//int count = 0;
		
		Review review = reviewService.getReview("1");
		
		assertNull(review);
		//assertEquals(count,5);
	}
	
	
	@Test
	public void insertReview() {
		Review review = new Review();
		review.setReviewContent("asdfdf");
		review.setMemberId("test1");
		review.setItemCode(11);
		int result = reviewService.insertReview(review);
		
		assertEquals(result, 1);
	}*/
	
	

}
