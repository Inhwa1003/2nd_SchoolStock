package com.school.schoolstock.domain.coupon_purchase.service;

import com.school.schoolstock.domain.coupon_purchase.repository.CouponPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponPurchaseService {
    @Autowired
    private CouponPurchaseRepository couponPurchaseRepository;

    // 쿠폰 구매 내역 등록
    public int setPurchaseRecord(String studentId, int couponNo, String couponName, int couponPrice, String purchaseState){
        return couponPurchaseRepository.setPurchaseRecord(
                studentId,
                couponNo,
                couponName,
                couponPrice,
                purchaseState
        );
    }

}
