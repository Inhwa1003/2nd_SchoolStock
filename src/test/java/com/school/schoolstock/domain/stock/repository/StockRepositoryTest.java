package com.school.schoolstock.domain.stock.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Slf4j
@SpringBootTest
public class StockRepositoryTest {
    @Autowired
    StockRepository stockRepository;
    @Test
    void getStockNameTest(){
        //NO
        //없는 주식번호(null 반환)
        Assertions.assertEquals(null,stockRepository.getStockName(111));
        //YES
        log.info("주식명 : " + stockRepository.getStockName(1));
    }
    @Test
    void getStockNameListTest(){
        //NO
        //입력값 존재x
        //YES
        log.info("주식명 리스트 : " + stockRepository.getStockNameList());
    }
    @Test
    void getStockInfoTest(){
        //NO
        //없는 주식번호 조회([]반환)
        Assertions.assertEquals(0,stockRepository.getStockInfo(111).size());
        Assertions.assertFalse(stockRepository.getStockInfo(111).size() > 0);
        //YES
        log.info("주식 기본 정보 : " + stockRepository.getStockInfo(1));
    }
    @Test
    void getStockPrice(){
        //NO
        //존재하지 않는 주식번호(null반환 리턴타입 int가 null을 받지 못해 BindingException예외발생)
        Assertions.assertThrows(BindingException.class, () -> stockRepository.getStockPrice(111));
        //YES
        log.info("주식 현재 가격 : " + stockRepository.getStockPrice(1));
    }
    @Test
    void getStockPriceChangeTest(){
        //NO
        //존재하지 않는 주식번호(null반환 리턴타입 int가 null을 받지 못해 BindingException예외발생)
        Assertions.assertThrows(BindingException.class, () -> stockRepository.getStockPriceChange(111));
        //YES
        log.info("주식 이전가 대비 가격 : " + stockRepository.getStockPriceChange(1));
    }
    @Test
    void getChangeRateTest(){
        //NO
        //존재하지 않는 주식번호(null반환 리턴타입 int가 null을 받지 못해 BindingException예외발생)
        Assertions.assertThrows(BindingException.class, () -> stockRepository.getChangeRate(111));
        //YES
        log.info("주식 등락률 : " + stockRepository.getChangeRate(1) + "%");
    }
    @Test
    void getPrevPointTest(){
        //NO
        //존재하지 않는 주식번호(null반환 리턴타입 int가 null을 받지 못해 BindingException예외발생)
        Assertions.assertThrows(BindingException.class, () -> stockRepository.getPrevPoint(111));
        //YES
        log.info("주식 이전가격 : " + stockRepository.getPrevPoint(1));
    }
    @Test
    void setStockPubBalanceTest(){
        //NO
        //없는 주식번호
        Assertions.assertEquals(false ,stockRepository.setStockPubBalance(1,1111));
        //DB 컬럼이 받을수 있는 범위 초과(DataIntegerityViolationException 발생)
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> stockRepository.setStockPubBalance(10000000, 1));
        //YES
        //발행잔량 보다 넘게 차감시 -값 들어감 체크해야할듯
        Assertions.assertEquals(true, stockRepository.setStockPubBalance(1000, 1));
        log.info("발행 개수 변경 결과 : " + stockRepository.setStockPubBalance(1,1 ));
    }
    @Test
    void getStockPubInfoTest(){
        //NO
        //없는 주식번호(null반환)
        Assertions.assertEquals(null, stockRepository.getStockPubInfo(111));
        Assertions.assertEquals(null, stockRepository.getStockPubInfo(111111));
        //YES
        log.info("발행 정보 :" + stockRepository.getStockPubInfo(1));
    }

}
