package com.school.schoolstock.domain.coupon.controller;

import com.school.schoolstock.domain.coupon.service.CouponService;
import com.school.schoolstock.domain.coupon.vo.Coupons;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CouponController {

    private final CouponService couponService;

    // 쿠폰 상점 화면 => 해당 페이지는 조회에 실패하면, JSON을 반환하는 것보단 에러상황을 Model에 담아서 화면에 보여주는 방식임.
    @GetMapping("/schoolstock/s/coupons")
    public String getCouponList(Model model) {

        try {
            List<Coupons> couponList = couponService.getCouponList();

            if (couponList == null || couponList.isEmpty()) {
                model.addAttribute("couponList", Collections.emptyList());
                model.addAttribute("code", "COUPON_NOT_FOUND");
                model.addAttribute("message", "등록된 쿠폰을 찾을 수 없습니다.");

                return "couponMarket";
            }

            model.addAttribute("couponList", couponList);
            model.addAttribute("code", "OK");
            model.addAttribute("message", "쿠폰 상점 목록이 정상적으로 조회되었습니다.");

            return "couponMarket";

        } catch (Exception e) {
            model.addAttribute("couponList", Collections.emptyList());
            model.addAttribute("code", "INTERNAL_SERVER_ERROR");
            model.addAttribute("message", "서버 내부 오류로 쿠폰 상점 목록 조회에 실패했습니다.");

            return "couponMarket";
        }
    }
}