package com.shop.myapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Item {
    private String itemCode;
    private String memberId;
    private String countryCode;
    private String itemName;
    private int itemPrice;
    private String itemImage;
    private String itemInfo;
    private int itemStock;
    private String itemBrand;
    private List<ItemOption> itemOptions;
    private Country country;
    private String shopName;
    private String isDelete;
    private Shop shop;

    public void calculateItemStock() {
        for (ItemOption itemOption : itemOptions) {
            itemStock += itemOption.getOptionStock();
        }
    }
}
