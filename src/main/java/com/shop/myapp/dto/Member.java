package com.shop.myapp.dto;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {
	private String memberId;
	private String memberPwd; 
	private LinkedList<String> memberLevel;
	private String memberLevelToString;
	private String memberName;
	private String memberEmail;
	private String memberAddress;
	private String memberDetailAddress;
	private String memberTel;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate memberBirth;
	private String isDelete;
	
    public void setMemberLevel(String memberLevel) {
    	this.memberLevelToString = memberLevel;
    	List<String> levelList = Arrays.asList(memberLevel.trim().split(","));
		this.memberLevel = new LinkedList<>(levelList);
    }

}
