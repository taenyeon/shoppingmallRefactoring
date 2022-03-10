package com.shop.myapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Country {

    private String countryCode;

    private String countryName;

    private int countryPostPrice;

//    국가별 배송비 random (10,000 ~ 15,000)

    @Builder
    public Country(String countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }
}
