package com.school.schoolstock.domain.coupon.dto.request;

// 쿠폰 상점에 있는 쿠폰의 쿠폰명과 쿠폰 포인트를 업데이트 할 때 필요한 요청값

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponUpdateRequest {
    private int couponNo;
    private String name;
    private int couponPoint;
}
