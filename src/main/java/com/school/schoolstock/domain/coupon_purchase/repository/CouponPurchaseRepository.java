package com.school.schoolstock.domain.coupon_purchase.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CouponPurchaseRepository {

    // 쿠폰 구매 내역 등록
    int setPurchaseRecord(String studentId, int couponNo, String couponName, int couponPrice, String purchaseState);

}
