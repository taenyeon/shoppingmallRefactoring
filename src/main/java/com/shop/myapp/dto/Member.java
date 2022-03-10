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
	private String businessRegistrationNo;
	private String businessName;
	private String businessInfo;
	private String isDelete;
	
    public void setMemberLevel(String memberLevel) {
    	System.out.println("객체접근");
    	this.memberLevelToString = memberLevel;
    	List<String> list = new ArrayList<>();
    	try {
    		list  =  Arrays.asList(memberLevel.trim().split(","));    		
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	LinkedList<String> levels = new LinkedList<>(list);
    	System.out.println("객체 ->"+levels.toString());
    	System.out.println("ToString -> "+memberLevelToString);
    	this.memberLevel = levels;
    }

}
