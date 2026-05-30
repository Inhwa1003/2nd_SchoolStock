package com.school.schoolstock.domain.coupon_purchase.vo;

import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@ToString

public class CouponPurchase {

    private int couponPurchaseNo;
    private String studentId;
    private int couponNo;
    private LocalDateTime purchaseDate;
    private int purchasePoint;
    private String name;
    private String purchaseState;
}
