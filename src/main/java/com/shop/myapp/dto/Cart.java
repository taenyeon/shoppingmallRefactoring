package com.shop.myapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
public class Cart {
    // view 에서 받을때, 사용하는 객체
    private String cartId;
    private String memberId;
    private String optionCode;
    private int amount;
    private ItemOption itemOption;

    public OrderDetail parseToOrderDetail(Order order){
        return OrderDetail.builder()
                .itemOption(this.itemOption)
                .orderCode(order.getOrderCode())
                .amount(this.amount)
                .optionCode(this.optionCode)
                .orderPrice(this.itemOption.getItem().getItemPrice())
                .postPrice(this.itemOption.getItem().getCountry().getCountryPostPrice())
                .order(order)
                .build();
    }

}
