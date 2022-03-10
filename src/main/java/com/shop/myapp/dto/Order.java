package com.shop.myapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter @Setter
public class Order {

    private String orderCode;

    private String memberId;

    private long totalPay;

    private String buyerName;

    private String buyerTel;

    private String buyerEmail;

    private String buyerAddr;

    private String buyerPostCode;

    private String isPaid;
    // iamport 결제 고유번호
    private String impUid;
    // 결제 된 금액 -> 환불시, 환불금 빠짐.
    private long change;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paidAt;

    private List<OrderDetail> orderDetails;

    public void setOrderCodeByDate(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        this.orderCode = now.format(formatter);

    }

    @Builder
    public Order(String orderCode, String memberId, long totalPay, String buyerName, String buyerTel, String buyerEmail, String buyerAddr, String buyerPostCode, String isPaid, String impUid, long change, LocalDateTime paidAt, List<OrderDetail> orderDetails) {
        this.orderCode = orderCode;
        this.memberId = memberId;
        this.totalPay = totalPay;
        this.buyerName = buyerName;
        this.buyerTel = buyerTel;
        this.buyerEmail = buyerEmail;
        this.buyerAddr = buyerAddr;
        this.buyerPostCode = buyerPostCode;
        this.isPaid = isPaid;
        this.impUid = impUid;
        this.change = change;
        this.paidAt = paidAt;
        this.orderDetails = orderDetails;
    }



    public Order() {
    }

    public String paidAtToString(){
        LocalDateTime d = LocalDateTime.parse(this.paidAt.toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return d.format(formatter);
    }
}
