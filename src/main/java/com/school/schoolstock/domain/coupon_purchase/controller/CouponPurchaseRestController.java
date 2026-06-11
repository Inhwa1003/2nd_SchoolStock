package com.school.schoolstock.domain.coupon_purchase.controller;

import com.school.schoolstock.domain.coupon_purchase.dto.CouponPurchaseRequest;
import com.school.schoolstock.domain.coupon_purchase.service.CouponPurchaseService;
import com.school.schoolstock.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/schoolstock/s/me/coupon-purchases")
public class CouponPurchaseRestController {

    private final CouponPurchaseService couponPurchaseService;

    @PostMapping
    public ApiResponse buyCoupon(
            @RequestBody CouponPurchaseRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        couponPurchaseService.buyCoupon(userDetails.getUsername(), request.getCouponNo());
        return ApiResponse.of(200, "쿠폰 구매가 완료되었습니다.", null);
    }
}