package com.school.schoolstock.domain.stock.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class StockManageResponse {
    private int stockNo;
    private String name;
    private int nowPoint;        // 현재가
    private int priceChange;     // 현재가-이전가
    private double changeRate;   // 등락률
    private String stockContent;       // 설명
    private int publicationBalance;    // 발행 잔량
    private int publicationPoint;      // 발행가
    private boolean tradeStarted;
}
