package com.shop.myapp.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.myapp.dto.Cart;
import com.shop.myapp.dto.FormList;
import com.shop.myapp.dto.MemberSession;
import com.shop.myapp.dto.Order;
import com.shop.myapp.dto.OrderDetail;
import com.shop.myapp.dto.Payment;
import com.shop.myapp.interceptor.Auth;
import com.shop.myapp.service.OrderService;

@Controller
@RequestMapping("/order")
@Auth(role = Auth.Role.USER)
public class OrderController {
    private final OrderService orderService;
    private final HttpSession session;

    public OrderController(OrderService orderService, HttpSession session) {
        this.orderService = orderService;
        this.session = session;
    }

    @PostMapping("/addForm")
    public String insertOrderForm(@ModelAttribute FormList formList, Model model) {
        List<String> cartCodes = formList.getCartCodes();
        cartCodes.removeIf(Objects::isNull);
        List<Cart> carts = orderService.getSelectCartByCartIds(formList.getCartCodes());
        model.addAttribute("carts", carts);
        return "order/orderForm";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Object> addOrder(@ModelAttribute FormList formList, @ModelAttribute Order order) throws Exception {
    	MemberSession member = (MemberSession) session.getAttribute("member");
        order.setMemberId(member.getMemberId());
        Order responseOrder = orderService.insertOrder(order, formList.getCartCodes());
        order.setOrderDetails(null);
        return ResponseEntity.ok(responseOrder);
    }

    @PostMapping("/{orderCode}/validate")
    @ResponseBody
    public ResponseEntity<Object> validateTotalPay(@RequestParam("imp_uid") String impUid, @PathVariable String orderCode) throws ParseException, JsonProcessingException {
        System.out.println("impUid = " + impUid);
        System.out.println("orderCode = " + orderCode);
        Payment payment = orderService.validateTotalPay(impUid, orderCode);
        if (payment == null) {
            return ResponseEntity.status(402).build();
        }
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/{orderCode}/cancel")
    @ResponseBody
    public ResponseEntity<Object> cancelOrder(@PathVariable String orderCode) {
        orderService.cancelOrder(orderCode); // 리턴 받을 필요 없어보임(?)
        return ResponseEntity.ok().build();
    }

    @GetMapping("/myOrder")
    public String myOrder(Model model) {
    	MemberSession member = (MemberSession) session.getAttribute("member");
        String memberId = member.getMemberId();
        List<Order> orders = orderService.myOrder(memberId);
        model.addAttribute("orders", orders);
        return "/order/myOrder";
    }

    ;

    @GetMapping("/{orderCode}")
    public String findByOrderCode(@PathVariable String orderCode, Model model) {
    	MemberSession member = (MemberSession) session.getAttribute("member");
        String memberId = member.getMemberId();
        Order order = orderService.findByOrderCode(orderCode);
        if (order.getMemberId().equals(memberId)) {
            model.addAttribute("order", order);
            return "/order/order";
        }
        throw new IllegalStateException("아이디 불일치(접근 권한 없음)");
    }


}
