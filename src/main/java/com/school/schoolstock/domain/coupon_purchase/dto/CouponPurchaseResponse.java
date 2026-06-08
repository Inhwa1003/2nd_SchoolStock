package com.school.schoolstock.domain.coupon_purchase.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

// 쿠폰 구매 내역 추가
public class CouponPurchaseResponse {
    private int couponPurchaseNo;
    private int couponNo;
    private String couponName;
    private int purchasePoint;
    private String purchaseState;
}
