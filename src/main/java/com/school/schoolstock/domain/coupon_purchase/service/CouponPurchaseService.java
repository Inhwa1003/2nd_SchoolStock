package com.school.schoolstock.domain.coupon_purchase.service;

public interface CouponPurchaseService {
    //쿠폰 구매
    void buyCoupon(String studentId, int couponNo);
}
