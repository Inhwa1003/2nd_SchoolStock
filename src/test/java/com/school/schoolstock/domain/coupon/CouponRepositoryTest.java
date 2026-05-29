package com.school.schoolstock.domain.coupon;

import com.school.schoolstock.domain.coupon.repository.CouponRepository;
import com.school.schoolstock.domain.coupon.vo.Coupons;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CouponRepositoryTest {

    @Autowired
    CouponRepository couponRepository;

    @Test
    void getCouponList_test() {
        // when
        List<Coupons> result = couponRepository.getCouponList();

        // then
        System.out.println("쿠폰 상점에 등록된 쿠폰: " + result);

        assertNotNull(result);
        assertEquals(3, result.size());

        assertEquals("간식 교환권", result.get(0).getName());
        assertEquals(500, result.get(0).getCouponPoint());

        assertEquals("청소당번 면제권", result.get(1).getName());
        assertEquals(3000, result.get(1).getCouponPoint());

        assertEquals("분리수거 면제권", result.get(2).getName());
        assertEquals(1000, result.get(2).getCouponPoint());
    }
}