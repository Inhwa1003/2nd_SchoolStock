package com.school.schoolstock.domain.order.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class OrderRequest {
    //주식번호
    private int stockNo;
    //주문포인트
    private int orderPoint;
    //주문개수
    private int orderAmount;
}
