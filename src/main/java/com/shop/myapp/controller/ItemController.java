package com.shop.myapp.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.myapp.dto.Item;
import com.shop.myapp.dto.ItemOption;
import com.shop.myapp.dto.MemberSession;
import com.shop.myapp.dto.Pagination;
import com.shop.myapp.interceptor.Auth;
import com.shop.myapp.service.FileService;
import com.shop.myapp.service.ItemService;
import com.shop.myapp.service.MemberService;

@Controller
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;
    private final FileService fileService;
    private final MemberService memberService;
    private final HttpSession session;

    public ItemController(ItemService itemService, FileService fileService, HttpSession session,MemberService memberService) {
        this.itemService = itemService;
        this.fileService = fileService;
        this.session = session;
        this.memberService = memberService;
    }

    @GetMapping("")
    public String getItems(@RequestParam(required = false, defaultValue = "1") int page, Model model) {
        Pagination pagination = itemService.getPaginationByPage(page,null);
        List<Item> items = itemService.getItems(pagination);
        model.addAttribute("items", items);
        for (Item item: items){
            System.out.println(item.getBusinessName());
        }
        model.addAttribute("pagination", pagination);
        return "item/items";

    }
    @GetMapping("/{itemCode}")
    public String getItemDetail(@PathVariable String itemCode, Model model,@RequestParam(required = false) String flag) {
        Item item = itemService.getItem(itemCode);
        if (flag != null){
            model.addAttribute("flag",flag);
        }
        model.addAttribute("item", item);
        return "item/item";
    }

    @Auth(role = Auth.Role.SELLER)
    @GetMapping("/add")
    public String createItemForm() {
        return "/item/addItemForm";
    }
    @Auth(role = Auth.Role.SELLER)
    @PostMapping("/add")
    public String createItem(Item item, MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request) throws IOException {
        String absolutePath = request.getServletContext().getRealPath("/resources/");
        MemberSession member = (MemberSession) request.getSession().getAttribute("member");
        item.setMemberId(member.getMemberId());
        item.setBusinessName(memberService.getMember(member.getMemberId()).getBusinessName());
        Map<String, String> fileInfo = fileService.boardFileUpload(file, absolutePath);
        item.setItemImage(fileInfo.get("path"));
        int itemResult = itemService.createItem(item);
        if (itemResult != 0) {
            redirectAttributes.addAttribute("itemCode", item.getItemCode());
            return "redirect:/item/{itemCode}";
        } else {
            throw new IllegalStateException("아이템 등록 실패");
        }
    }
    @Auth(role = Auth.Role.SELLER)
    @GetMapping("/{itemCode}/update")
    public String updateItemForm(@PathVariable String itemCode, Model model) {
    	MemberSession member = (MemberSession) session.getAttribute("member");
        if (!itemService.validateAccessToItem(itemCode, member)) {
            throw new IllegalStateException("권한 없음");
        }
        Item item = itemService.getItem(itemCode);
        model.addAttribute("item", item);
        return "/item/updateItemForm";
    }

    @Auth(role = Auth.Role.SELLER)
    @PostMapping("/{itemCode}/update")
    public String updateItem(@PathVariable String itemCode, Item item,MultipartFile file,HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {
        String absolutePath = request.getServletContext().getRealPath("/resources/");
        MemberSession member = (MemberSession) session.getAttribute("member");
        if (itemService.validateAccessToItem(itemCode, member)) {
            item.setItemCode(itemCode);
        for (ItemOption itemOption : item.getItemOptions()){
            itemOption.setItemCode(itemCode);
        }
        Map<String, String> fileInfo = fileService.boardFileUpload(file, absolutePath);
        if (!(fileInfo == null)){
            item.setItemImage(fileInfo.get("path"));
        }
            itemService.updateItem(item);
            redirectAttributes.addAttribute("itemCode", itemCode);
            return "redirect:/item/{itemCode}";
        }
        return "redirect:/item";
    }
    @Auth(role = Auth.Role.SELLER)
    @GetMapping("/{itemCode}/delete")
    public String deleteItem(@PathVariable String itemCode) {
    	MemberSession member = (MemberSession) session.getAttribute("member");
        if (itemService.validateAccessToItem(itemCode, member)) {
            itemService.deleteItem(itemCode);
            return "redirect:/item";
        }
        return "redirect:/item";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false, defaultValue = "1") int page, @RequestParam("q") String search, Model model) {
        Pagination pagination = itemService.getPaginationByPage(page,search);
        List<Item> items = itemService.search(search, pagination);
        model.addAttribute("pagination", pagination);
        model.addAttribute("items", items);
        return "item/items";
    }


}
