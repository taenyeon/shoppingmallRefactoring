package com.shop.myapp.repository;

import com.shop.myapp.dto.Order;
import com.shop.myapp.dto.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDetailRepository {
    int insertOrderDetails(List<OrderDetail> orderDetails);
    OrderDetail findByOrderDetailCode(String orderDetailCode);
    int deleteOrderDetail(String orderCode);
    int updatePostedStatusByOrderCode(String orderCode);
    int updatePostedStatusByOrderDetailCodeAfterReview(String orderDetailCode);
    int updateWhenCancel(String orderDetailCode);
    List<OrderDetail> findByMemberIdForSeller(@Param("memberId") String memberId,@Param("search") String search, @Param("type") String type);
    int updatePostedStatusByOrderDetailCode(@Param("orderDetailCode") String orderDetailCode,@Param("postedStatus") String postedStatus);
}
