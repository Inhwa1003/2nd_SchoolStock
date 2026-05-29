package com.school.schoolstock.domain.coupon.vo;

import lombok.Getter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupons {

    private int couponNo;
    private String name;
    private int couponPoint;

}
