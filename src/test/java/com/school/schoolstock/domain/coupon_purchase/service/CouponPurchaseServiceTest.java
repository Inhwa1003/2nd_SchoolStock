//package com.school.schoolstock.domain.coupon_purchase.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@Transactional
//@Slf4j
//@SpringBootTest
//public class CouponPurchaseServiceTest {
//
//    @Autowired
//    private CouponPurchaseService couponPurchaseService;
//
//    @Test
//    void buyCouponTest() {
//        // YES — 포인트 충분 + 보유쿠폰<3 (test01: 30000P, 쿠폰0)
//        Assertions.assertTrue(couponPurchaseService.buyCoupon("test01", 1));
//        log.info("구매 성공");
//
//        // NO — 없는 쿠폰 -> false
//        Assertions.assertFalse(couponPurchaseService.buyCoupon("test01", 99999));
//    }
//
//}
