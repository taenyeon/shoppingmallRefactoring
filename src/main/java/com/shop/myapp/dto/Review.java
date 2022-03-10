package com.shop.myapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Review {
	
	private int reviewCode;
	private int itemCode;
	private String orderDetailCode;
	private String memberId;
	private String reviewContent;
	private int reviewStar;

}
	    



