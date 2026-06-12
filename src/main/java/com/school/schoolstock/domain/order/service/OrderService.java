package com.school.schoolstock.domain.order.service;

import com.school.schoolstock.domain.order.dto.request.BuySellOrderRequest;
import com.school.schoolstock.domain.order.dto.response.StockOrderResponse;

import java.util.List;

public interface OrderService {
    //주식상세 : ComboBox 매도/매수 의 등록된 주문 전체 조회
    List<StockOrderResponse> getStockOrders(int stockNo, String content);
    //매도
    String setSellOrder(String studentId, BuySellOrderRequest request);
    //매수
    String setBuyOrder(String studentId, BuySellOrderRequest request);
}
