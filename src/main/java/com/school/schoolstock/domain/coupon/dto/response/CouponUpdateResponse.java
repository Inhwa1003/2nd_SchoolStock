package com.school.schoolstock.domain.coupon.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CouponUpdateResponse {

    private int couponNo;
    private String name;
    private int couponPoint;

}
