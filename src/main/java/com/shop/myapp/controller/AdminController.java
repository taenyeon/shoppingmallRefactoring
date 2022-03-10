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
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView getMemberList(@RequestParam String chkInfo, @RequestParam String condition) {
		List<Member> members = memberService.getMembers(chkInfo, condition);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("modal/memberTable");
		mv.addObject("members", members);

		return mv;
	}
	
	@GetMapping("/{memberId}/detail")
	public ModelAndView detail(@PathVariable String memberId, HttpServletRequest request) {
		log.info("member detail!!!");
		MemberSession mSession = (MemberSession)request.getSession().getAttribute("member");
		Member member = memberService.getMemberAdmin(memberId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("modal/memberDetail");
		mv.addObject("member", member);

		return mv;
	}
	
	@PostMapping("/memberUpdate")
	public String memberUpdate(@ModelAttribute Member member) {
		log.info("member update!!!");
		
		memberService.updateByAdmin(member);
		
		return "redirect:/admin/list";
	}
	
	@GetMapping("/chart")
	public String chart(Model model) {
		log.info("member chart!!!");
		List<Chart> tOrderChart = memberService.getTotalPayChart();
		LinkedList<String> totalPay = new LinkedList<>();
		LinkedList<String> paidAt = new LinkedList<>();
		
		for (Chart chart : tOrderChart) {
			totalPay.add(chart.getTotalPay());
			paidAt.add("'"+chart.getPaidAt().toString()+"'");
		}
		System.out.println(totalPay);
		System.out.println(paidAt);
		model.addAttribute("totalPay", totalPay);
		model.addAttribute("paidAt", paidAt);
		
		return "/order/totalOrderChart";
	}
}
