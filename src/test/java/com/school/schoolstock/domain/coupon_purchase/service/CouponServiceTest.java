package com.school.schoolstock.domain.coupon_purchase.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest

public class CouponServiceTest {

    @Autowired
    private CouponPurchaseService couponPurchaseService;

    @Test
    void setPurchaseRecordTest(){
        int result = couponPurchaseService.setPurchaseRecord(
                "abc",
                1,
                "매점 1000원 할인 쿠폰",
                1000,
                "NOT_USED"
        );

        assertEquals(1, result);

        System.out.println("쿠폰 구매 내역 등록 결과 = " + result);
    }

}
