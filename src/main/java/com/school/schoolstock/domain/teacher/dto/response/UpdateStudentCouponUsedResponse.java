package com.school.schoolstock.domain.teacher.dto.response;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UpdateStudentCouponUsedResponse {
    private int couponPurchaseNo;
    private String name;
    private int purchasePoint;
    private String purchaseState;
}
