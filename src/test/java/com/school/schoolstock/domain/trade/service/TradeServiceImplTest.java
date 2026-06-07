package com.school.schoolstock.domain.trade.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TradeServiceImplTest {

    @Autowired
    private TradeServiceImpl tradeServiceImpl;

    @Test
    void setTradeServiceTest(){
        boolean result = tradeServiceImpl.setMatchedOrder(
                1,
                2
        );
        System.out.println("구매 내역 등록: " + result);
    }


}
