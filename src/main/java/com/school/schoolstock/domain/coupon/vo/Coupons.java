package com.school.schoolstock.domain.coupon.vo;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Coupons {

    private int couponNo;
    private String name;
    private int couponPoint;

}
