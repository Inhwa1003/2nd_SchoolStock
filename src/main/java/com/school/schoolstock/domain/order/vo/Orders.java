package com.school.schoolstock.domain.order.vo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Orders {

    private int orderNo;
    private String studentId;
    private int stockNo;
    private String orderContent; // BUY, SELL
    private int orderPoint;      // 한 주당 가격
    private int amount;
    private String orderState;   // PENDING, MATCHED, CANCELED
    private LocalDateTime orderDate;

}