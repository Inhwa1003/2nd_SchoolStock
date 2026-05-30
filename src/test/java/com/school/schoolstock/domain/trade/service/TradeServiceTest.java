package com.school.schoolstock.domain.trade.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TradeServiceTest {

    @Autowired
    private TradeService tradeService;

    @Test
    void setTradeServiceTest(){
        boolean result = tradeService.setMatchedOrder(
                1,
                2
        );
        System.out.println("구매 내역 등록: " + result);
    }


}
