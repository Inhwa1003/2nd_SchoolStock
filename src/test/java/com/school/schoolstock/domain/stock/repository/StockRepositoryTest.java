package com.school.schoolstock.domain.stock.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class StockRepositoryTest {
    @Autowired
    StockRepository stockRepository;
    @Test
    void getStockNameListTest(){
        System.out.println(stockRepository.getStockNameList().toString());
    }
    @Test
    void getStockInfoTest(){
        System.out.println(stockRepository.getStockInfo(1));
    }
    @Test
    void getStockPrice(){
        System.out.println(stockRepository.getStockPrice(1));
    }
    @Test
    void getStockPriceChangeTest(){
        System.out.println(stockRepository.getStockPriceChange(1));
    }
    @Test
    void getChangeRateTest(){
        System.out.println(stockRepository.getChangeRate(1));
    }
    @Test
    void getPrevPointTest(){
        System.out.println(stockRepository.getPrevPoint(1));
    }
    @Test
    void setStockPubBalanceTest(){
        System.out.println(stockRepository.setStockPubBalance(1,1 ));
    }
    @Test
    void getStockPubInfoTest(){
        System.out.println(stockRepository.getStockPubInfo(1));
    }

}
