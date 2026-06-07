package com.school.schoolstock.domain.student.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class MyStockResponse {
    private String stockName; //주식이름
    private int stockAmount; //주식개수
    private int nowPoint; //현재가
    private int averagePoint; //평균단가
    private int purchasePoint; //총 구매 비용
    private int stockProfit; //수익금
}
