package com.school.schoolstock.domain.coupon_purchase.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class CouponPurchase {
    private int couponPurchaseNo;
    private String studentId;
    private int couponNo;
    private LocalDateTime purchaseDate;
    private int purchasePoint;
    private String name;
    private String purchaseState;
}
