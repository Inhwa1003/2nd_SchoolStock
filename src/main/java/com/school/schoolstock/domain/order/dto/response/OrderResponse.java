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
    private String orderContent;
    private int orderPoint;
    private int orderAmount;
}
