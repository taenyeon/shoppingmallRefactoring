package com.shop.myapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shop.myapp.dto.Pagination;
import com.shop.myapp.dto.Review;

@Mapper
public interface ReviewRepository {
	List<Review> findAll(@Param("itemCode") String itemCode);
	List<Review> getReivewList(); 
	Review findByReviewCode(String reviewCode);
	int insertReview(Review review);
	int deleteReview(String review);
	int updateReview(@Param("reviewCode")String reviewCode,@Param("reviewContent") String reviewContent);
	//2개의 값을 받는 경우 에러가 발생할 수도 있어, @Param("reviewCode") 이런식으로 구분을 해서 입력 해준다.
	Review findByReviewId(String memberId);

}
