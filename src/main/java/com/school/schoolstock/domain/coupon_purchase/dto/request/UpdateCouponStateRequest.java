package com.school.schoolstock.domain.coupon_purchase.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

// 학생 보유 쿠폰 상태 변경 요청
public class UpdateCouponStateRequest {

    private int couponNo;
    private String name;

}
