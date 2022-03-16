package com.shop.myapp.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.shop.myapp.interceptor.Auth;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.myapp.dto.Chart;
import com.shop.myapp.dto.Member;
import com.shop.myapp.dto.MemberSession;
import com.shop.myapp.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@Auth(role = Auth.Role.ADMIN)
@RequestMapping("/admin")
public class AdminController {
	private final MemberService memberService;
	
	public AdminController(MemberService memberService) {
        this.memberService = memberService;
    }

	@GetMapping("/list")
	public String memberListForm() {
		return "/members/memberListForm";
	}

	@PostMapping("/list")
	public String getMemberList(@RequestParam String chkInfo, @RequestParam String condition, Model model) {
		List<Member> members = memberService.getMembers(chkInfo, condition);
		model.addAttribute("members",members);
		return "/modal/memberTable";
	}
	
	@GetMapping("/{memberId}/detail")
	public String detail(@PathVariable String memberId, HttpServletRequest request, Model model) {
		MemberSession mSession = (MemberSession)request.getSession().getAttribute("member");
		Member member = memberService.getMemberAdmin(memberId);
		model.addAttribute("member",member);
		return "/modal/memberDetail";
	}
	
	@PostMapping("/memberUpdate")
	public String memberUpdate(@ModelAttribute Member member) {
		memberService.updateByAdmin(member);
		return "redirect:/admin/list";
	}
	
	@GetMapping("/chart")
	public String chart(Model model) {
		List<Chart> tOrderChart = memberService.getTotalPayChart();
		LinkedList<String> totalPay = new LinkedList<>();
		LinkedList<String> paidAt = new LinkedList<>();
		
		for (Chart chart : tOrderChart) {
			totalPay.add(chart.getTotalPay());
			paidAt.add("'"+chart.getPaidAt().toString()+"'");
		}
		model.addAttribute("totalPay", totalPay);
		model.addAttribute("paidAt", paidAt);
		
		return "/order/totalOrderChart";
	}
}
