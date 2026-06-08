package com.school.schoolstock.domain.coupon_purchase.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
@SpringBootTest
public class CouponPurchaseServiceTest {

    @Autowired
    private CouponPurchaseService couponPurchaseService;

    @Test
    void buyCouponTest() {
        // YES — 포인트 충분 + 보유쿠폰 < 3
        // DB 데이터 확인!
        boolean success = couponPurchaseService.buyCoupon("789", 1);

        Assertions.assertTrue(success);
        
        log.info("쿠폰 구매 성공 정상 처리");
        
        // NO — 없는 쿠폰 -> null
        boolean failResponse = couponPurchaseService.buyCoupon("test01", 99999);

        Assertions.assertFalse(failResponse);

        log.info("없는 쿠폰 및 사용자 구매 실패 정상 처리");
    }
}