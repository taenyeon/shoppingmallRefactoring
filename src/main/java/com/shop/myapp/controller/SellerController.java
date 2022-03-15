package com.shop.myapp.controller;

import java.util.List;

import com.shop.myapp.dto.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.myapp.interceptor.Auth;
import com.shop.myapp.service.ItemService;
import com.shop.myapp.service.MemberService;
import com.shop.myapp.service.OrderDetailService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/seller")
public class SellerController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderDetailService orderDetailService;
    private final HttpSession session;

    public SellerController(HttpSession session, MemberService memberService, ItemService itemService, OrderDetailService orderDetailService) {
        this.session = session;
        this.memberService = memberService;
        this.itemService = itemService;
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/{memberId}")
    public String getSellerInfo(@PathVariable String memberId,
                                @RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(value = "q",required = false) String search,
                                Model model){
        Member seller = memberService.getMember(memberId);
        Pagination pagination = itemService.getPaginationByMemberId(page, memberId);
        List<Item> items = memberService.getSellerItems(memberId,pagination,search);
        model.addAttribute("seller",seller);
        model.addAttribute("items",items);
        model.addAttribute("pagination",pagination);
        return "/seller/sellerView";
    }
    @Auth(role = Auth.Role.SELLER)
    @GetMapping("/{memberId}/update")
    public String updateSellerInfoForm(@PathVariable String memberId,Model model){
        Member seller = memberService.getMember(memberId);
        model.addAttribute("seller",seller);
        return "/modal/sellerModal";
    }
    @Auth(role = Auth.Role.SELLER)
    @PostMapping("/{memberId}/update")
    public String updateSellerInfo(@PathVariable String memberId, Member member, RedirectAttributes redirectAttributes){
        member.setMemberId(memberId);
        memberService.updateSellerInfo(member);
        redirectAttributes.addAttribute("memberId",memberId);
        return "redirect:/seller/{memberId}";
    }

    @GetMapping("/{memberId}/order")
    public String orders(@PathVariable String memberId,
                         @RequestParam(value = "q",required = false) String search,
                         String type,
                         Model model){
        System.out.println(type);
        MemberSession member = (MemberSession) session.getAttribute("member");
        if (!memberId.equals(member.getMemberId())){
            throw new IllegalStateException("권한 없음");
        }
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailByItemWriter(memberId,search,type);
        model.addAttribute("orderDetails",orderDetails);
                return "/seller/sellerItemOrder";
    }


}
