package com.school.schoolstock.domain.stock.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder

// 주식 상세 페이지 UI 로딩할 때, 뿌려주는 response.
public class StockDetailPageResponse {
    //주식번호
    private int stockNo;
    //주식이름
    private String name;
    //주식설명
    private String stockContent;
    //발행잔량
    private int publicationBalance;
    //발행가격
    private int publicationPoint;
    //현재가
    private int nowPoint;
    //이전가
    private int prevPoint;
    //(현재가 - 이전가)
    private int priceChange;
    //등락률
    private double changeRate;
}
