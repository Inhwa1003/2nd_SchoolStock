package com.school.schoolstock.domain.trade.service;

public interface TradeService {
    boolean setMatchedOrder(int buyOrderNo, Integer sellOrderNo);
}
