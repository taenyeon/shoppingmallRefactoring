package com.shop.myapp.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chart {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate paidAt;
	private String totalPay;
}
