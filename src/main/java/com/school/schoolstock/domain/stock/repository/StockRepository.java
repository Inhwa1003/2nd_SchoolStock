package com.school.schoolstock.domain.stock.repository;

import com.school.schoolstock.domain.stock.vo.StockVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StockRepository {
    // 주식명 리스트 조회
    List<StockVO> getStockNameList();
    // 주식 기본 정보 조회
    List<StockVO> getStockInfo(int stockNo);
    // 주식 현재가격 조회
    int getStockPrice(int stockNo);
    // 주식 이전가 대비 가격 조회
    int getStockPriceChange(int stockNo);
    // 주식 등락률 조회
    double getChangeRate(int stockNo);
    // 주식 이전가격(전장마감가) 조회
    int getPrevPoint(int stockNo);
    // 발행 개수 변경
    boolean setStockPubBalance(int buyAmount, int stockNo);
    // 발행 정보 조회
    StockVO getStockPubInfo(int stockNo);
}
