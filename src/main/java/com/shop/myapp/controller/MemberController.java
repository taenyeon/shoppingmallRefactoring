package com.shop.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.myapp.dto.Member;
import com.shop.myapp.dto.MemberSession;
import com.shop.myapp.interceptor.Auth;
import com.shop.myapp.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final HttpSession session;

    public MemberController(MemberService memberService, HttpSession session) {
        this.memberService = memberService;
        this.session = session;
    }

    @GetMapping("/join")
    public String joinForm() {
    	//authService.checkMemberId("");
    	log.info("joinForm");
    	
    	return "/members/join";
    }
   
    @PostMapping("/join")
    public String join(@ModelAttribute Member member) {
    	// 에러가 있는지 검사
    	log.info("join");
    	int isSuccess = memberService.insertMember(member);
    	System.out.println("회원가입 성공("+isSuccess+")");
    	return "redirect:/";
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
    	request.getSession().invalidate();
    	
    	return "redirect:/";
    }

    @Auth(role = Auth.Role.USER)
    @GetMapping("/{memberId}/info")
    public String infoForm(@PathVariable String memberId, Model model) {
        MemberSession member1 = (MemberSession) session.getAttribute("member");
        if (!member1.getMemberId().equals(memberId)){
            throw new IllegalStateException("권한 없음");
        }
        log.info("memberUpdateForm");
    	Member member = memberService.getMember(memberId); 
    	model.addAttribute("member", member);
    	return "/members/info";
    }
    
    @Auth(role = Auth.Role.USER)
    @PostMapping("/{memberId}/update")
    public String update(@ModelAttribute Member member) {
        MemberSession member1 = (MemberSession) session.getAttribute("member");
        if (!member1.getMemberId().equals(member.getMemberId())){
            throw new IllegalStateException("권한 없음");
        }
    	// 에러가 있는지 검사
    	log.info("Edit member information.");
    	
    	int isSuccess = memberService.updateMember(member);
    	System.out.println(isSuccess);
    	System.out.println("회원정보 수정 성공("+isSuccess+")");
    	return "redirect:/";
    }
    
    @Auth(role = Auth.Role.USER)
    @GetMapping("/{memberId}/delete")
    public String delete(@PathVariable String memberId, HttpServletRequest request) {
        MemberSession member1 = (MemberSession) session.getAttribute("member");
        if (!member1.getMemberId().equals(memberId)){
            throw new IllegalStateException("권한 없음");
        }
    	// 에러가 있는지 검사
    	log.info("delete member.");
    	
    	int isDelete = memberService.deleteMember(memberId);
    	System.out.println("회원탈퇴 성공("+isDelete+")");
    	log.info("update complete.");
    	request.getSession().invalidate();
    	return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm() {
    	log.info("loginForm");
    	return "/members/login";
    }
    
    @PostMapping("/login")
    public String login(@ModelAttribute Member member, HttpServletRequest request){
        System.out.println("PWD : "+member.getMemberPwd());
    	log.info("login");
    	Member mem = memberService.loginMember(member);
    	
    	MemberSession mSession = new MemberSession();
    	mSession.setMemberId(mem.getMemberId());
    	mSession.setMemberLevel(mem.getMemberLevel());
    	
    	request.getSession().setAttribute("member",mSession);

    	return "redirect:/";
    }

    @GetMapping("/{memberId}")
    @ResponseBody
    @Auth(role = Auth.Role.USER)
    public ResponseEntity<Object> getMemberInfo(@PathVariable String memberId){
        MemberSession member1 = (MemberSession) session.getAttribute("member");
        if (!member1.getMemberId().equals(memberId)){
            throw new IllegalStateException("권한 없음");
        }
        Member member = memberService.getMember(memberId);
        return ResponseEntity.ok(member);
    }
}