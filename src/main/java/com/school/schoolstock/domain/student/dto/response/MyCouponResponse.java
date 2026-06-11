package com.school.schoolstock.domain.student.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class MyCouponResponse {
    private String name;          // 구매 당시 쿠폰명 (coupon_purchase.name 스냅샷)
    private int purchasePoint; // purchase_point → purchasePoint 자동매핑 (구매 당시 가격)
    private int purchaseStatus;
}
