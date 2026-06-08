package com.school.schoolstock.domain.coupon_purchase.service;

import com.school.schoolstock.domain.coupon_purchase.dto.CouponPurchaseResponse;
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
        // studentId가 123인 값이 없으면, 실패로 뜸. DB 데이터 확인!
        CouponPurchaseResponse response = couponPurchaseService.buyCoupon("123", 1);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getCouponNo());
        Assertions.assertEquals("NOT_USED", response.getPurchaseState());
        Assertions.assertTrue(response.getPurchasePoint() > 0);
        Assertions.assertNotNull(response.getCouponName());

        log.info("구매 성공");
        log.info("couponPurchaseNo = {}", response.getCouponPurchaseNo());
        log.info("couponNo = {}", response.getCouponNo());
        log.info("couponName = {}", response.getCouponName());
        log.info("purchasePoint = {}", response.getPurchasePoint());
        log.info("purchaseState = {}", response.getPurchaseState());

        // NO — 없는 쿠폰 -> null
        CouponPurchaseResponse failResponse = couponPurchaseService.buyCoupon("test01", 99999);

        Assertions.assertNull(failResponse);
        log.info("없는 쿠폰 구매 실패 정상 처리");
    }
}