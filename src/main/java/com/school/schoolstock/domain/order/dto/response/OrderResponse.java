package com.school.schoolstock.domain.order.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class OrderResponse {
    private int orderNo;
    private String studentId;
    private int stockNo;
        //종류 (BUY / SELL)
    private String orderContent;
    //가격
    private int orderPoint;
    //수량
    private int orderAmount;
    private String orderState;
    private LocalDateTime orderDate;
}
