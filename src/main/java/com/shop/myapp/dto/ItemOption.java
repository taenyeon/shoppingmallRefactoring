package com.shop.myapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemOption {

    private String optionCode;
    private String itemCode;
    private String optionName;
    private int optionPriceUd;
    private int optionStock;
    private Item item;
    private String isDelete;
}
