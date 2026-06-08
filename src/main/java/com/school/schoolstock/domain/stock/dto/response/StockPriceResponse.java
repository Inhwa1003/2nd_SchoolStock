package com.school.schoolstock.domain.stock.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class StockPriceResponse {
    //현재가
    private int nowPoint;
    //(현재가 - 이전가)
    private int priceChange;
    //등락률
    private double changeRate;
}
