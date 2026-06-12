package com.school.schoolstock.domain.coupon.controller;

import com.school.schoolstock.domain.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@Controller
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/schoolstock/s/coupons")
    public String getCouponList(Model model) {

        model.addAttribute("couponList", couponService.getCouponList());

        return "couponMarket";
    }
}