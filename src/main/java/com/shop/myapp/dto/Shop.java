package com.shop.myapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class Shop {
    private String shopName;
    private String memberId;
    private String shopInfo;
    private String shopRegistrationNo;
}
