package com.shop.myapp.dto;

import java.util.LinkedList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSession {
	private String memberId;
	private LinkedList<String> memberLevel;
}
