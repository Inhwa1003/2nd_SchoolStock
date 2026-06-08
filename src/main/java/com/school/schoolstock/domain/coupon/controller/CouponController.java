package com.school.schoolstock.domain.coupon.controller;

import com.school.schoolstock.domain.coupon.service.CouponService;
import com.school.schoolstock.domain.coupon.vo.Coupons;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CouponController {

    private final CouponService couponService;

    // 쿠폰 상점 화면 + 쿠폰 목록 조회
    @GetMapping("/schoolstock/s/coupons")
    public String getCouponList(Model model) {

        List<Coupons> couponList = couponService.getCouponList();

        model.addAttribute("couponList", couponList);

        return "couponMarket";
    }
}