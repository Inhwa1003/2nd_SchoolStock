package com.school.schoolstock.domain.teacher.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UpdateStudentCouponUsedRequest {

    private int couponPurchaseNo;
}