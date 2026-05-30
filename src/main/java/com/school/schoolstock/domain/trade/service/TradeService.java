package com.school.schoolstock.domain.trade.service;

import com.school.schoolstock.domain.trade.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeService {
    @Autowired
    private TradeRepository tradeRepository;

    // 주문 매칭 완료 거래내역 추가
    public boolean setMatchedOrder(int buyOrderNo, Integer sellOrderNo){
        return tradeRepository.setMatchedOrder(
                buyOrderNo,
                sellOrderNo
        );
    }
}
