package com.school.schoolstock.domain.coupon.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CouponUpdateResponse {

    private int couponNo;
    private String name;
    private int couponPoint;

}
