package com.school.schoolstock.domain.coupon.service;

import com.school.schoolstock.domain.coupon.vo.Coupons;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void getCouponListTest(){
        List<Coupons> couponList = couponService.getCouponList();
        assertNotNull(couponList);
        System.out.println("구매한 쿠폰: "+ couponList);
    }
}
