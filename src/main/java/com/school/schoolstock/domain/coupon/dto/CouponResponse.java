package com.school.schoolstock.domain.coupon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CouponResponse {

    private int couponNo;
    private String name;
    private int couponPoint;

}
