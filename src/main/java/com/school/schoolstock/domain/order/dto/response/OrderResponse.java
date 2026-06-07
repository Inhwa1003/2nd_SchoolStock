package com.school.schoolstock.domain.order.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class OrderResponse {
    //종류 (BUY / SELL)
    private String orderContent;
    //가격
    private int orderPoint;
    //수량
    private int orderAmount;
}
