package com.shop.myapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class OrderDetail {

    private String orderCode;
    private String orderDetailCode;
    private String optionCode;
    private ItemOption itemOption;
    private int amount;
    private String postedStatus;
    private int orderPrice;
    private int postPrice;
    private Order order;

    public OrderDetail() {
    }

    @Builder
    public OrderDetail(String orderCode, String orderDetailCode, String optionCode, ItemOption itemOption, int amount, String postedStatus,int orderPrice,int postPrice, Order order) {
        this.orderCode = orderCode;
        this.orderDetailCode = orderDetailCode;
        this.optionCode = optionCode;
        this.itemOption = itemOption;
        this.amount = amount;
        this.postedStatus = postedStatus;
        this.orderPrice = orderPrice;
        this.postPrice = postPrice;
        this.order = order;
    }
}
