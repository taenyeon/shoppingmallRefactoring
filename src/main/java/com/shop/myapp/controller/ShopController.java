package com.shop.myapp.controller;

import java.util.List;

import com.shop.myapp.dto.*;
import com.shop.myapp.service.ShopService;
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
import com.shop.myapp.service.OrderDetailService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ItemService itemService;
    private final OrderDetailService orderDetailService;
    private final ShopService shopService;
    private final HttpSession session;

    public ShopController(HttpSession session, ItemService itemService, OrderDetailService orderDetailService, ShopService shopService) {
        this.session = session;
        this.itemService = itemService;
        this.orderDetailService = orderDetailService;
        this.shopService = shopService;
    }

    @GetMapping("/{shopName}")
    public String getSellerInfo(@PathVariable String shopName,
                                @RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(value = "q", required = false) String search,
                                Model model) {
        Shop shop = shopService.findByShopName(shopName);
        Pagination pagination = itemService.getPaginationByMemberId(page, shopName);
        List<Item> items = itemService.getSellerItemByMemberId(shopName, pagination, search);
        model.addAttribute("shop", shop);
        model.addAttribute("items", items);
        model.addAttribute("pagination", pagination);
        return "/shop/shopView";
    }

    @Auth(role = Auth.Role.SELLER)
    @GetMapping("/{shopName}/update")
    public String updateSellerInfoForm(@PathVariable String shopName, Model model) {
        Shop shop = shopService.findByShopName(shopName);
        model.addAttribute("shop", shop);
        return "/modal/sellerModal";
    }

    @Auth(role = Auth.Role.SELLER)
    @PostMapping("/{shopName}/update")
    public String updateSellerInfo(@PathVariable String shopName,Shop shop, RedirectAttributes redirectAttributes) {
        MemberSession member = (MemberSession) session.getAttribute("member");
        if (!member.getMemberId().equals(shop.getMemberId())) {
            throw new IllegalStateException("권한 없음");
        }
        shop.setShopName(shopName);
        shopService.updateByShopName(shop);
        redirectAttributes.addAttribute("shopName", shopName);
        return "redirect:/shop/{shopName}";
    }

    @GetMapping("/{shopName}/order")
    public String orders(@PathVariable String shopName,
                         @RequestParam(value = "q", required = false) String search,
                         String type,
                         Model model) {
        Shop shop = shopService.findByShopName(shopName);
        MemberSession member = (MemberSession) session.getAttribute("member");
        if (!shop.getMemberId().equals(member.getMemberId())) {
            throw new IllegalStateException("권한 없음");
        }
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailByItemWriter(shopName, search, type);
        model.addAttribute("orderDetails", orderDetails);
        return "/shop/shopItemOrder";
    }


}
