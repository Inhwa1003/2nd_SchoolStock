package com.school.schoolstock.domain.trade.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TradeRepositoryTest {

    @Autowired
    TradeRepository tradeRepository;

    @Test
    void setMatchedOrderTest() {
        // given
        int buyOrderNo = 8;
        Integer sellOrderNo = null;

        // when
        boolean result = tradeRepository.setMatchedOrder(buyOrderNo, sellOrderNo);

        // then
        System.out.println("거래내역 추가 결과: " + result);
        assertTrue(result);
    }
}
