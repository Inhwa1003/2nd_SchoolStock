package com.school.schoolstock.domain.coupon.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CouponDeleteRequest {

    private int couponNo;
}