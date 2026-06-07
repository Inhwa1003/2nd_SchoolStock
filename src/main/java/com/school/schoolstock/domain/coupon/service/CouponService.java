package com.school.schoolstock.domain.coupon.service;

import com.school.schoolstock.domain.coupon.vo.Coupons;

import java.util.List;

public interface CouponService {
    // 쿠폰 상점 목록 조회
    List<Coupons> getCouponList();
}
