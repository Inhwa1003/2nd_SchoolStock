package com.school.schoolstock.domain.coupon_purchase.controller;

import com.school.schoolstock.domain.coupon_purchase.dto.CouponPurchaseRequest;
import com.school.schoolstock.domain.coupon_purchase.service.CouponPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/schoolstock/s/coupons")
public class CouponPurchaseController {

    private final CouponPurchaseService couponPurchaseService;

    // 임시 테스트 화면
    @GetMapping("/test")
    public String couponPurchaseTestPage() {
        return "couponMarket";
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> buyCoupon(
            @RequestBody CouponPurchaseRequest request,
            Authentication authentication
    ) {
        if (authentication == null || !authentication.isAuthenticated()) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", 401);
            error.put("code", "UNAUTHORIZED");
            error.put("message", "인증 토큰이 없거나 유효하지 않습니다.");
            return ResponseEntity.status(401).body(error);
        }

        String studentId = authentication.getName();

        boolean result = couponPurchaseService.buyCoupon(
                studentId,
                request.getCouponNo()
        );

        if (!result) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", 400);
            error.put("code", "BAD_REQUEST");
            error.put("message", "요청 값이 올바르지 않습니다.");
            return ResponseEntity.badRequest().body(error);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("data", null);
        response.put("message", "쿠폰 구매가 완료되었습니다.");

        return ResponseEntity.ok(response);
    }
}