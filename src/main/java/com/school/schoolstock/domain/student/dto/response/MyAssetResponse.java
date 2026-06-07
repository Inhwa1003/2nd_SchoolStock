package com.school.schoolstock.domain.student.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyAssetResponse {
    private int totalValue;    //총자산   getMyValue
    private int myPoint;       //가용포인트 getMyPoint
    private int totalProfit;   //총손익   getTotalProfit
    private int couponAmount;   //보유쿠폰수 getMyCouponAmount
    private List<MyStockResponse> myStocks; //보유 주식정보(주식명, 보유수량, 현재가격, 평균단가, 총 구매 비용, 수익금)
}
