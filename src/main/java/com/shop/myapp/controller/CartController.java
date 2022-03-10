package com.shop.myapp.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

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
import com.shop.myapp.dto.MemberSession;
import com.shop.myapp.interceptor.Auth;
import com.shop.myapp.service.CartService;

@Controller
@RequestMapping("/cart")
@Auth(role = Auth.Role.USER)
public class CartController {

    private final CartService cartService;
    private final HttpSession session;

    public CartController(CartService cartService, HttpSession session) {
        this.cartService = cartService;
        this.session = session;
    }

    @PostMapping("/add")
    public String insertCart(@ModelAttribute Cart cartItem) {
        MemberSession member = (MemberSession) session.getAttribute("member");
        Optional<Cart> myCart = cartService.findMyCartByOptionCode(member.getMemberId(), cartItem.getOptionCode());
        if (myCart.isPresent()) {
            cartService.amountSetByCartId(myCart.get().getCartId(),"+");
        } else {
            cartItem.setMemberId(member.getMemberId());
            cartItem.setAmount(1);
            cartService.insertCart(cartItem);
        }

        return "redirect:/cart/myCart";
    }

    @GetMapping("/myCart")
    public String myCart(Model model) {
        MemberSession member = (MemberSession) session.getAttribute("member");
        List<Cart> carts = cartService.findCartDetailByMemberId(member.getMemberId());
        model.addAttribute("carts", carts);
        return "/cart/myCart";
    }

    @PostMapping("/{cartCode}/delete")
    @ResponseBody
    public ResponseEntity<Object> deleteCart(@PathVariable String cartCode) {
        MemberSession member = (MemberSession) session.getAttribute("member");
        cartService.validateMemberId(cartCode,member.getMemberId());
        Optional<Cart> cartOptional = cartService.findByCartId(cartCode);
        Cart cart = cartOptional.orElseThrow(() -> new IllegalStateException("cart 정보 없음"));
        if (cart.getMemberId().equals(member.getMemberId())) {
            int result = cartService.deleteByCartId(cartCode);
            if (result > 0) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(402).build();
            }
        }
        throw new IllegalStateException("사용자 정보 불일치");
    }

    @PostMapping("/{cartCode}/setAmount")
    @ResponseBody
    public ResponseEntity<Object> setCartAmount(@PathVariable String cartCode, @RequestParam String mathSign) {
        MemberSession member = (MemberSession) session.getAttribute("member");
        Optional<Cart> cartOptional = cartService.findByCartId(cartCode);
        Cart cart = cartOptional.orElseThrow(() -> new IllegalStateException("cart 정보 없음"));
        // 갯수가 0일경우, 처리 로직 추가 필요.
        if (cart.getMemberId().equals(member.getMemberId())) {
            int result = cartService.amountSetByCartId(cartCode, mathSign);
            if (result == 0) {
                return ResponseEntity.status(402).build();
            }
            return ResponseEntity.ok().build();
        }
        throw new IllegalStateException("사용자 정보 불일치");
    }
}
