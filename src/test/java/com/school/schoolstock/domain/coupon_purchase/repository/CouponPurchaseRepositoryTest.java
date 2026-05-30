package com.school.schoolstock.domain.coupon_purchase.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CouponPurchaseRepositoryTest {

    @Autowired
    CouponPurchaseRepository couponPurchaseRepository;

    @Test
    void setPurchaseRecord_test() {
        // given
        String studentId = "abc";
        int couponNo = 1;
        String couponName = "간식 교환권";
        int couponPrice = 500;
        String purchaseState = "NOT_USED";

        // when
        int result = couponPurchaseRepository.setPurchaseRecord(
                studentId,
                couponNo,
                couponName,
                couponPrice,
                purchaseState
        );

        // then
        System.out.println("쿠폰 구매 내역 등록 결과: " + result);
        assertEquals(1, result);
    }
}