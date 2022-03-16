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

    /**
     * 장바구니 상품들을 이용하여 주문 작성
     * @param order 주문자 정보
     * @param cartIds 장바구니에서 선택한 구매 상품
     * @return 주문 DB 추가 결과
     */
    public Order insertOrder(Order order, List<String> cartIds) {
        order.setOrderCodeByDate();
        List<Cart> carts = cartService.findSelectCartByCartIds(cartIds);
        List<OrderDetail> orderDetails = new ArrayList<>();
        int total = 0;
        for (Cart cart : carts) {
            // 제고 확인
            boolean isValidated = optionStockValidate(cart);
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

    /**
     * 주문 상품 구매 가능 여부 검증
     * @param cart 장바구니 상품 정보
     * @return 상품 삭제 여부 , 상품 옵션 삭제 여부 , 상품 재고량 검증 결과
     */
    public boolean optionStockValidate(Cart cart){
        ItemOption itemOption = cart.getItemOption();
        return itemOption.getOptionStock() != 0 && !itemOption.getIsDelete().equals("1") && !itemOption.getItem().getIsDelete().equals("1");
    }

    /**
     * 장바구니에서 구매하고자 선택한 상품들 정보 불러옴
     * @param cartIds 장바구니 번호
     * @return 선택한 상품들 정보
     */
    public List<Cart> getSelectCartByCartIds(List<String> cartIds) {
        return cartService.findSelectCartByCartIds(cartIds);
    }

    /**
     * 결제 완료시, IamPort API 서버에서 받은 결제 정보와 해당 서버 DB에 저장된 주문 정보가 일치하는지 검증
     * DB에 저장된 총 결제 금액과 IamPort 에서 받은 결제 정보 일치 시, 해당 주문 상태 변경
     * 불일치 시, 해당 결제 환불 처리
     * @param impUid 결제 완료시, IamPort API 서버에서 응답으로 보낸 결제 번호
     * @param orderCode 주문 번호
     * @return 검증이 완료된 결제 정보
     * @throws ParseException 응답받은 String 타입의 json 데이터 파싱 실패
     * @throws JsonProcessingException 환불 요청시, 요청 json 데이터 변환 실패
     */
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

    /**
     * 주문 취소 (현재 구현 x) -> 각각의 상세 주문 상품 취소로 구현
     * @param orderCode 주문 번호
     * @return 주문 DB 삭제 결과
     */
    public int cancelOrder(String orderCode) {
        orderDetailService.deleteOrderDetail(orderCode);
        return orderRepository.deleteOrderByOrderCode(orderCode);

    }

    /**
     * 주문 조회
     * @param orderCode 주문번호
     * @return 주문 정보
     */
    public Order findByOrderCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode).orElseThrow(() -> new IllegalStateException("주문을 찾을 수 없습니다."));
    }

    /**
     * 구매자 주문 내역 조회
     * @param memberId 회원 아이디
     * @return 주문 내역
     */
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

    /**
     * 환불시, 환불한 금액 저장 -> 환불 진행시, IamPort API 서버와 비교 작업
     * @param orderDetail 환불한 주문 상세 상품
     * @return 주문 DB 수정 결과
     */
    public int updateChangeWhenCancel(OrderDetail orderDetail){
        return orderRepository.updateChangeWhenCancel(orderDetail);
    }

}
