package com.shop.myapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shop.myapp.dto.*;
import com.shop.myapp.repository.OrderRepository;
import org.apache.ibatis.session.SqlSession;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = {Exception.class})
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final ItemOptionService itemOptionService;
    private final CartService cartService;
    private final IamPortService iamPortService;


    public OrderService(@Autowired SqlSession sqlSession, @Lazy OrderDetailService orderDetailService, ItemOptionService itemOptionService, CartService cartService, IamPortService iamPortService) {
        this.orderRepository = sqlSession.getMapper(OrderRepository.class);
        this.orderDetailService = orderDetailService;
        this.itemOptionService = itemOptionService;
        this.cartService = cartService;
        this.iamPortService = iamPortService;
    }

    public Order insertOrder(Order order, List<String> cartIds) throws Exception {
        order.setOrderCodeByDate();
        List<Cart> carts = cartService.findSelectCartByCartIds(cartIds);
        List<OrderDetail> orderDetails = new ArrayList<>();
        int total = 0;
        for (Cart cart : carts) {
            // 제고 확인
            boolean isValidated = optionStockValidate(cart);
            System.out.println(isValidated);
            if (isValidated){
            // cart 내부 매서드로 cart -> orderDetail 로 변환
            OrderDetail orderDetail = cart.parseToOrderDetail(order);
            // orderDetail 삽입
            orderDetails.add(orderDetail);
            // 상품 갯수
            int amount = cart.getAmount();
            // 상품 가격
                int itemPrice = cart.getItemOption().getItem().getItemPrice();
                // 상품 배송비
                int countryPostPrice = cart.getItemOption().getItem().getCountry().getCountryPostPrice();
                // (상품 가격 * 갯수) + (상품 배송비 * 갯수)
            total += (itemPrice * amount) + (countryPostPrice * amount);
            }
        }
        order.setTotalPay(total);
        order.setOrderDetails(orderDetails);
        orderRepository.insertOrder(order);
        orderDetailService.insertOrderDetails(orderDetails);
        return order;
    }

    public boolean optionStockValidate(Cart cart){
        ItemOption itemOption = cart.getItemOption();
        return itemOption.getOptionStock() != 0 && !itemOption.getIsDelete().equals("1") && !itemOption.getItem().getIsDelete().equals("1");
    }

    public List<Cart> getSelectCartByCartIds(List<String> cartIds) {
        return cartService.findSelectCartByCartIds(cartIds);
    }

    public Payment validateTotalPay(String impUid, String orderCode) throws ParseException, JsonProcessingException {
        // 주문 코드에 해당하는 주문 객체 가져옴.
            Order order = findByOrderCode(orderCode);
            // impUid 를 IamPort 서버에 전달하여 결제 정보 응답받음.
            Payment payment = iamPortService.getImpAttributes(impUid);
            // 주문 객체의 결제 금액과 IamPort 의 결제 금액이 불일치할 경우,
            if (!(order.getTotalPay() == payment.getAmount())) {
                // IamPort 서버에 결제 전액 취소 요청
                orderDetailService.orderRefund(payment);
                throw new IllegalStateException("위조된 결제입니다. \n 자동 환불처리됩니다.");
        }
            // 결제 금액이 일치할 경우,

                // 주문 상태 변경
                orderRepository.updateIsPaidIntByOrderCode(orderCode,payment);
                // 배송 상태 변경
                orderDetailService.updatePostedStatusByOrderCode(orderCode);
                // 주문 상품의 재고 변경
                itemOptionService.modifyItemOptionAfterPay(order.getOrderDetails());
                // 장바구니 비워줌
                cartService.deleteByMemberId(order.getMemberId());
                // 결제 정보 리턴
                return payment;
    }

    public int cancelOrder(String orderCode) {
        orderDetailService.deleteOrderDetail(orderCode);
        return orderRepository.deleteOrderByOrderCode(orderCode);

    }


    public Order findByOrderCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode).orElseThrow(() -> new IllegalStateException("주문을 찾을 수 없습니다."));
    }
    public List<Order> myOrder(String memberId){
        List<Order> orders = orderRepository.findOrderByMemberId(memberId);
        for (Order order : orders){
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for(OrderDetail orderDetail : orderDetails){
                Optional<ItemOption> itemOptionOptional = itemOptionService.findByOptionCodeForOrder(orderDetail.getOptionCode());
                ItemOption itemOption = itemOptionOptional.orElseThrow(() -> new IllegalStateException("없는 아이템"));
                orderDetail.setItemOption(itemOption);
            }
        }
        return orders;
    }

    public int updateChangeWhenCancel(OrderDetail orderDetail){
        return orderRepository.updateChangeWhenCancel(orderDetail);
    }

}
