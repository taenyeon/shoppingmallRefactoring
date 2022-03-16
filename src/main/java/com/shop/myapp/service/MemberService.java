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

    /**
     * 회원 정보 조회
     * @param memberId 회원 아이디
     * @return 회원 상세 정보
     */
    public Member getMember(String memberId) {
        return memberRepository.
                findById(memberId).
                orElseThrow(() -> new IllegalStateException(memberId + " 라는 id의 member 없음"));
    }

    /**
     * 추후, 수정 필요
     * @param memberId
     * @return
     */
    public Member getMemberAdmin(String memberId) {
        return memberRepository.
                findByIdAdmin(memberId).
                orElseThrow(() -> new IllegalStateException(memberId + " 라는 id의 member 없음"));
    }

    /**
     *
     * @param chkInfo 검색 옵션
     * @param condition 검색어
     * @return 검색어에 해당하는 회원들
     */
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

    /**
     * 회원가입 -> 아이디 중복 여부 확인
     * @param member 회원 정보
     * @return 회원 DB 추가 결과
     */
    public int insertMember(Member member) {
    	validateDuplicateMember(member);
        member.setMemberPwd(BCrypt.hashpw(member.getMemberPwd(), BCrypt.gensalt()));
        return memberRepository.insertMember(member);
    }

    /**
     * 로그인 -> 아이디, 비밀번호 일치 여부 확인
     * @param member 회원 정보 (아이디, 비밀번호)
     * @return 회원 DB 조회 결과
     */
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

    /**
     * 회원가입시, 아이디 중복 검증
     * @param member 회원 정보
     */
    public void validateDuplicateMember(Member member) {
    	memberRepository.findById(member.getMemberId())
    			.ifPresent(m -> {
    				throw new IllegalStateException("이미 존재하는 회원입니다.");
    			});
    }

    /**
     * 회원 정보 수정
     * @param member 회원 정보
     * @return 회원 DB 수정 결과
     */
    public int updateMember(Member member) {
        	if(member.getMemberPwd() != null && !member.getMemberPwd().equals("")) {
        		member.setMemberPwd(BCrypt.hashpw(member.getMemberPwd(), BCrypt.gensalt()));
        	}
        return memberRepository.updateMember(member);
    }

    /**
     * 회원탈퇴처리(isDeleted)
     * @param memberId 회원 아이디
     * @return 회원 DB 삭제 처리(isDeleted) 결과
     */
    public int deleteMember(String memberId) {
    	return memberRepository.deleteMember(memberId);
    }

    /**
     * 상점 등록 상품 조회 추후, 상품 서비스 로직에 통합
     * @param memberId 회원 아이디
     * @param pagination 페이징 객체
     * @param search 검색어
     * @return 검색어에 해당하는 해당 상점 등록 상품들 12개
     */
    public List<Item> getSellerItems(String memberId, Pagination pagination,String search){
        return itemService.getSellerItemByMemberId(memberId, pagination,search);
    }

    /**
     * 셀러 정보 수정
     * @param member 회원 정보 (상점 정보)
     * @return 회원 DB 수정 결과
     */
    public int updateSellerInfo(Member member){
        return memberRepository.updateSeller(member);
    }

    /**
     * 관리자 권한으로 특정 회원 정보 수정 -> 추후 관리자 서비스에 통합
     * @param member 회원 정보
     * @return 회원 DB 수정 결과
     */
    public int updateByAdmin(Member member) {
        member.setMemberPwd(BCrypt.hashpw(member.getMemberPwd(), BCrypt.gensalt()));
    	return memberRepository.updateByAdmin(member);
    }

    /**
     * 관리자 권한으로 해당 달의 총 판매 금액 통계 확인 -> 추후 관리자 서비스에 통합
     * @return 해당 달의 총 판매 금액
     */
	public List<Chart> getTotalPayChart() {
        return memberRepository.getTotalPayChart();
	}
}