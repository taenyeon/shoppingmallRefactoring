package com.shop.myapp.dto;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter @Setter
public class Member {
	@NotBlank(message = "아이디를 입력해주세요.")
	// 아이디 정규식
	// 시작은 영어
	// '_'를 제외한 특수문자 불가
	// 영어, 숫자, '_' 포함 5~11자리
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{4,11}$",
			message = "아이디는 영어, 숫자, '_' 포함 5~11자리로 입력해주세요.")
	private String memberId;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	// 비밀번호 정규식
	// 영어, 숫자, 특수문자 포함 8~15자리
	@Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
			message = "비밀번호는 영어, 숫자, 특수문자 포함 8~15자리로 입력해주세요.")
	private String memberPwd;

	private LinkedList<String> memberLevel;

	private String memberLevelToString;

	@NotBlank(message = "이름을 입력해주세요.")
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣]{2,7}$",
			message = "이름은 한글로 3~6자리로 입력해주세요.")
	private String memberName;

	@NotBlank(message = "이메일을 입력해주세요.")
	@Email
	// 이메일 정규식
	// 영어, 숫자, 일부 특수문자 포함 앞자리
	// @ 포함
	// 영어, 숫자, 일부 특수문자 포함 뒷자리
	// '.'뒤에 영어 2~3자리
	@Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",
			message = "이메일 형식에 맞게 입력해주세요.")
	private String memberEmail;

	@NotBlank(message = "주소를 입력해주세요.")
	private String memberAddress;

	@NotBlank(message = "상세주소를 입력해주세요.")
	private String memberDetailAddress;

	@NotBlank(message = "전화번호를 입력해주세요.")
	// 휴대폰 정규식
	// 앞 숫자 3자리
	// 중간 숫자 3~4자리
	// 뒤 숫자 4자리
	@Pattern(regexp = "^\\\\d{3}-\\\\d{3,4}-\\\\d{4}$",
			message = "전화번호 형식에 맞게 입력해주세요.")
	private String memberTel;

//	@NotBlank(message = "생일을 입력해주세요.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate memberBirth;

	private String isDelete;
	
    public void setMemberLevel(String memberLevel) {
    	this.memberLevelToString = memberLevel;
    	List<String> levelList = Arrays.asList(memberLevel.trim().split(","));
		this.memberLevel = new LinkedList<>(levelList);
    }

}
