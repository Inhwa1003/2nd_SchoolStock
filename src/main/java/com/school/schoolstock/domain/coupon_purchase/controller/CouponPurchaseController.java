package com.school.schoolstock.domain.coupon_purchase.controller;

import com.school.schoolstock.domain.coupon_purchase.dto.CouponPurchaseRequest;
import com.school.schoolstock.domain.coupon_purchase.dto.CouponPurchaseResponse;
import com.school.schoolstock.domain.coupon_purchase.service.CouponPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schoolstock/s/me/coupon-purchases")
public class CouponPurchaseController {

    private final CouponPurchaseService couponPurchaseService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> buyCoupon(
            @RequestBody CouponPurchaseRequest request,
            // Spring Security때문에 HttpSession이 아니라, Authentication 사용
            Authentication authentication
    ) {
        System.out.println("쿠폰 구매 API Controller 진입");

        if (authentication == null || !authentication.isAuthenticated()) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", 401);
            error.put("code", "UNAUTHORIZED");
            error.put("message", "인증 토큰이 없거나 유효하지 않습니다.");
            return ResponseEntity.status(401).body(error);
        }

        String studentId = authentication.getName();

        CouponPurchaseResponse data = couponPurchaseService.buyCoupon(
                studentId,
                request.getCouponNo()
        );

        if (data == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", 400);
            error.put("code", "BAD_REQUEST");
            error.put("message", "요청 값이 올바르지 않습니다.");
            return ResponseEntity.badRequest().body(error);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("data", data);
        response.put("message", "쿠폰 구매 내역이 정상적으로 등록되었습니다.");

        return ResponseEntity.ok(response);
    }
}