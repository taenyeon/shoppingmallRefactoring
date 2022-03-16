package com.shop.myapp.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.myapp.dto.Pagination;
import com.shop.myapp.dto.Review;
import com.shop.myapp.repository.ReviewRepository;


@Service
@Transactional
public class ReviewService {
	
	private ReviewRepository reviewRepository;
	private final OrderDetailService orderDetailService;
	
	public ReviewService(@Autowired SqlSession sqlSession, OrderDetailService orderDetailService) {
		this.reviewRepository = sqlSession.getMapper(ReviewRepository.class);

		this.orderDetailService = orderDetailService;
	}

	/**
	 * 
	 * @param reviewCode
	 * @return
	 */
	public Review getReview(String reviewCode) {
		return reviewRepository.findByReviewCode(reviewCode).orElseThrow(()->new IllegalStateException("리뷰를 찾을 수 없습니다."));
	}
	
	
	 public List<Review> getReviews(String itemCode) {
	        return reviewRepository.findAll(itemCode);
	    }

	    public int insertReview(Review review) {
			orderDetailService.updatePostedStatusByOrderDetailCodeAfterReview(review.getOrderDetailCode());
	       return reviewRepository.insertReview(review);
	    }

	    public int deleteReview(String review) {
	        return reviewRepository.deleteReview(review);
	    }

	    public int updateReview(String reviewCode, String reviewContent) {
	     return reviewRepository.updateReview(reviewCode, reviewContent);
	    }
	

	    public Review findByReviewId(String memberId){
	        return reviewRepository.findByReviewId(memberId);

	    }

}
