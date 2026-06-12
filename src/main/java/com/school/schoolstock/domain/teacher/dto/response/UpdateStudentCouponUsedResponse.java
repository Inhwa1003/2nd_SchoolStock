package com.school.schoolstock.domain.teacher.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStudentCouponUsedResponse {
    private int couponPurchaseNo;
    private String name;
    private int purchasePoint;
    private String purchaseState;
}
