package com.shop.myapp.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.shop.myapp.dto.Chart;
import com.shop.myapp.dto.Item;
import com.shop.myapp.dto.Pagination;
import org.apache.ibatis.session.SqlSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.myapp.dto.Member;
import com.shop.myapp.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
    private final ItemService itemService;
    private final MemberRepository memberRepository;

    public MemberService(@Autowired SqlSession sqlSession, ItemService itemService) {
        this.itemService = itemService;
        this.memberRepository = sqlSession.getMapper(MemberRepository.class);
    }

    public Member getMember(String memberId) {
        return memberRepository.
                findById(memberId).
                orElseThrow(() -> new IllegalStateException(memberId + " 라는 id의 member 없음"));
    }
    
    public Member getMemberAdmin(String memberId) {
        return memberRepository.
                findByIdAdmin(memberId).
                orElseThrow(() -> new IllegalStateException(memberId + " 라는 id의 member 없음"));
    }

    public List<Member> getMembers(String chkInfo, String condition) {
        List<Member> members = new ArrayList<>();
        List<Member> memberLevel = new ArrayList<>();
        try{
        	members = memberRepository.findAll(chkInfo, condition);
        	if(chkInfo.equals("member_level")) {
        		String upperCondition = condition.toUpperCase();
        		for (Member member : members) {
        			String lastLevel = member.getMemberLevel().getLast();
					if(lastLevel.contains(upperCondition)) {
						memberLevel.add(member);
					}
				}
        		return memberLevel;
        	}
        }catch (Exception e) {
			e.printStackTrace();
		}
        return members;
    }

    public int insertMember(Member member) {
    	validateDuplicateMember(member);
        member.setMemberPwd(BCrypt.hashpw(member.getMemberPwd(), BCrypt.gensalt()));
        return memberRepository.insertMember(member);
    }

    public Member loginMember(Member member) {
        String userPwd = member.getMemberPwd();
        //로그인한 유저 id를 조회한다.
        Member loginMember = memberRepository.findById(member.getMemberId()).
        		orElseThrow(() -> new IllegalStateException("아이디가 틀리거나 없는 회원입니다.") );
        if (BCrypt.checkpw(userPwd, loginMember.getMemberPwd())) {
            return loginMember;
        } else {
        	throw new IllegalStateException("비밀번호 오류입니다.");
        }
    }
    
    public void validateDuplicateMember(Member member) {
    	memberRepository.findById(member.getMemberId())
    			.ifPresent(m -> {
    				throw new IllegalStateException("이미 존재하는 회원입니다.");
    			});
    }

    public int updateMember(Member member) {
        	if(member.getMemberPwd() != null && !member.getMemberPwd().equals("")) {
        		member.setMemberPwd(BCrypt.hashpw(member.getMemberPwd(), BCrypt.gensalt()));
        	}
        return memberRepository.updateMember(member);
    }
    
    public int deleteMember(String memberId) {
    	return memberRepository.deleteMember(memberId);
    }

    public List<Item> getSellerItems(String memberId, Pagination pagination,String search){
        return itemService.getSellerItemByMemberId(memberId, pagination,search);
    }

    public int updateSellerInfo(Member member){
        return memberRepository.updateSeller(member);
    }
    
    public int updateByAdmin(Member member) {
        member.setMemberPwd(BCrypt.hashpw(member.getMemberPwd(), BCrypt.gensalt()));
    	return memberRepository.updateByAdmin(member);
    }

	public List<Chart> getTotalPayChart() {
        return memberRepository.getTotalPayChart();
	}
}