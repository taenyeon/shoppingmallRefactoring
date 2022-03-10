package com.shop.myapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.myapp.dto.OrderDetail;
import com.shop.myapp.dto.Payment;
import com.shop.myapp.repository.OrderDetailRepository;
import org.apache.ibatis.session.SqlSession;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = {Exception.class})
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final IamPortService iamPortService;
    private final OrderService orderService;
    private final ItemOptionService itemOptionService;

    public OrderDetailService(@Autowired SqlSession sqlSession, IamPortService iamPortService, @Lazy OrderService orderService, ItemOptionService itemOptionService) {
        this.orderDetailRepository = sqlSession.getMapper(OrderDetailRepository.class);
        this.iamPortService = iamPortService;
        this.orderService = orderService;
        this.itemOptionService = itemOptionService;
    }

    public void insertOrderDetails(List<OrderDetail> orderDetails) {
        orderDetailRepository.insertOrderDetails(orderDetails);

    }

    public void deleteOrderDetail(String orderCode) {
        orderDetailRepository.deleteOrderDetail(orderCode);
    }

    public void updatePostedStatusByOrderCode(String orderCode) {
        orderDetailRepository.updatePostedStatusByOrderCode(orderCode);
    }

    public OrderDetail findByOrderDetailCode(String orderDetailCode) {
        return orderDetailRepository.findByOrderDetailCode(orderDetailCode);
    }

    public boolean orderDetailRefund(OrderDetail orderDetail) throws ParseException, JsonProcessingException {
        // 환불에 사용할 imp_uid 와 amount 를 저장할 HashMap 선언.
        Map<String, Object> refundDetail = new HashMap<>();
        // json 형태의 String 으로 전달하기 위해서 ObjectMapper 선언.
        ObjectMapper objectMapper = new ObjectMapper();
        // 갯수,가격,배송비를 계산한 환불액.
        long refund = ((long) orderDetail.getAmount() * orderDetail.getOrderPrice()) + ((long) orderDetail.getAmount() * orderDetail.getPostPrice());
        // 현재 DB에 저장된 총 환불 금액.
        long change = orderDetail.getOrder().getChange();
        // 결제 코드인 imp_uid map 에 저장.
        refundDetail.put("imp_uid", orderDetail.getOrder().getImpUid());
        // 환불액을 map 에 저장.
        refundDetail.put("amount", refund);
        // String 형태로 보내야하기 때문에, ObjectMapper 를 통해 map 을 String 형태로 다시 저장.
        String refundDetailString = objectMapper.writeValueAsString(refundDetail);
        // 현재까지 환불된 금액과 iamPort 에서 응답받은 금액이 같은지 확인하고 같으면 컨트롤러 진행
        // 불일치시, IamPort 에서 전달한 메세지의 IllegalStateException 발생. (동시 환불 요청 or 환불 금액 불일치)
        if (iamPortService.cancel(refundDetailString) == (change+ refund)) {
            orderDetailRepository.updateWhenCancel(orderDetail.getOrderDetailCode());
        }
        return false;
    }
    public void orderRefund(Payment payment) throws ParseException, JsonProcessingException {
        // 환불에 사용할 imp_uid 와 amount 를 저장할 HashMap 선언.
        Map<String, Object> refundDetail = new HashMap<>();
        // json 형태의 String 으로 전달하기 위해서 ObjectMapper 선언.
        ObjectMapper objectMapper = new ObjectMapper();
        // 갯수,가격,배송비를 계산한 환불액.
        long refund = payment.getAmount();
        // 결제 코드인 imp_uid map 에 저장.
        refundDetail.put("imp_uid", payment.getImpUid());
        // 환불액을 map 에 저장.
        refundDetail.put("amount", refund);
        String refundDetailString = objectMapper.writeValueAsString(refundDetail);
        iamPortService.cancel(refundDetailString);
    }
    public boolean orderCancelService(OrderDetail orderDetail) throws ParseException, JsonProcessingException {
        // 환불이 일치했을때,
        if (orderDetailRefund(orderDetail)){
            // 배송 상태 환불로 변경.
            updateWhenCancel(orderDetail.getOrderDetailCode());
            // 주문의 환불액 갱신.
            orderService.updateChangeWhenCancel(orderDetail);
            // 환불시, 아이템 갯수 증가.
            itemOptionService.modifyItemOptionAfterRefund(orderDetail);
            // 트렌젝션 처리로 인해 오류 발생시, 롤백.
            return true;
        }
        return false;
    }

    public int updateWhenCancel(String orderDetailCode) {
        return orderDetailRepository.updateWhenCancel(orderDetailCode);
    }

    public List<OrderDetail> getOrderDetailByItemWriter(String memberId,String search,String type){
        return orderDetailRepository.findByMemberIdForSeller(memberId,search,type);
    }

    public int updatePostedStatusByOrderDetailCode(String orderDetailCode, String postedStatus) throws ParseException, JsonProcessingException {
        if (postedStatus.equals("Refund")){
            OrderDetail orderDetail = findByOrderDetailCode(orderDetailCode);
            orderCancelService(orderDetail);
        }
        return orderDetailRepository.updatePostedStatusByOrderDetailCode(orderDetailCode, postedStatus);
    }

    public int updatePostedStatusByOrderDetailCodeAfterReview(String orderDetailCode){
        return orderDetailRepository.updatePostedStatusByOrderDetailCodeAfterReview(orderDetailCode);
    }
}
