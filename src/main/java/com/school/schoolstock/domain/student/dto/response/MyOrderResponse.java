package com.school.schoolstock.domain.student.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class MyOrderResponse {
    private int orderNo;
    private String orderContent;
    private int orderPoint;
    private int amount;
    private LocalDateTime orderDate;
    private String orderState;
}
