package com.school.schoolstock.domain.order.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder

// 매수, 매도 주문 토글로 조회할 때, Response
public class StockOrderResponse {
    private String orderContent;
    private int orderPoint;
    private int orderAmount;
}
