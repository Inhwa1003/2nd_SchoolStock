package com.school.schoolstock.domain.trade.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TradeRepository {
    // 주문 매칭 완료 거래내역 추가
  boolean setMatchedOrder(int buyOrderNo, Integer sellOrderNo);

}
