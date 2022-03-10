package com.shop.myapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Getter
@Setter
public class Payment {
    private String paymentCode;
    private String impUid; // null
    private String name;
    private long amount;
    private String buyerName;
    private String buyerEmail;
    private String buyerTel;
    private String buyerAddr;
    private String buyerPostCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paidAt;
    private String status;
    private String buyerPCC;

    @Builder
    public Payment(String impUid, String name, long amount, String buyerName, String buyerEmail, String buyerTel, String buyerAddr, String buyerPostCode, long paidAt) {
        this.impUid = impUid;
        this.name = name;
        this.amount = amount;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.buyerTel = buyerTel;
        this.buyerAddr = buyerAddr;
        this.buyerPostCode = buyerPostCode;
        this.paidAt =  stringToLocalDateTime(paidAt);
    }

    public LocalDateTime stringToLocalDateTime(long paidAt){
        long sec = paidAt*1000L;
        return Instant.ofEpochMilli(sec)
                .atZone(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();
    }
}
