package com.school.schoolstock.domain.stock.service;

import com.school.schoolstock.domain.stock.dto.response.StockDetailPageResponse;
import com.school.schoolstock.domain.stock.dto.response.StockListResponse;
import com.school.schoolstock.domain.stock.dto.response.StockPriceResponse;

import java.util.List;

public interface StockService {
    //현재가 조회
    int getStockPrice(int stockNo);
    //가격(현재가, (현재가 - 이전가), 등락률) 비동기 조회
    StockPriceResponse getStockPriceInfo(int stockNo);
    //주식목록 페이지
    List<StockListResponse> getStockList();
    //주식상세 페이지
    StockDetailPageResponse getStockDetail(int stockNo);
}
