package com.school.schoolstock.domain.coupon_purchase.service;

import com.school.schoolstock.domain.coupon_purchase.dto.CouponPurchaseResponse;

public interface CouponPurchaseService {
    //쿠폰 구매
    CouponPurchaseResponse buyCoupon(String studentId, int couponNo);
}
