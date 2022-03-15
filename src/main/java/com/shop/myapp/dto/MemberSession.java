package com.shop.myapp.dto;

import java.util.LinkedList;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberSession {
	private String memberId;
	private LinkedList<String> memberLevel;

	public MemberSession() {
	}

	public MemberSession(String memberId, LinkedList<String> memberLevel) {
		this.memberId = memberId;
		this.memberLevel = memberLevel;
	}
}
