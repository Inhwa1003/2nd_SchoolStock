package com.school.schoolstock.domain.order.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
// 매수, 매도 기능(POST)할 때 사용하는 Request DTO
public class BuySellOrderRequest {
    //주식번호
    private int stockNo;
    //주문포인트
    private int orderPoint;
    //주문개수
    private int orderAmount;
}
