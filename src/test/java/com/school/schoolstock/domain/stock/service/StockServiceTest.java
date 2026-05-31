package com.school.schoolstock.domain.stock.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
@SpringBootTest
public class StockServiceTest {
    @Autowired
    private StockServiceImpl stockService;
    @Test
    void setSellOrderTest(){
        //NO
        //없는 학생으로 매도 진행시 "보유 주식량보다 많은 매도 요청은 할 수 없습니다." 반환 학생 없으면 0반환해서 생기는 문제
        log.info(stockService.setSellOrder("testid1111", 1000, 1, 1));
        //없는 주식 번호 매도 요청(발행잔량 체크하는 부분에서 null로 값을 조회 하면서 NullPointException 발생)
        Assertions.assertThrows(NullPointerException.class,() -> stockService.setSellOrder("abc", 1000, 2, 1111));
        //YES
        //학생간거래 매칭실패 매도 대기
        log.info(stockService.setSellOrder("abc", 11111, 2, 1));
        //학생간거래 매칭성공 매도 완료
        log.info(stockService.setSellOrder("abc", 800, 2, 1));
        //발행잔량 없고 학생간 거래 하는도중 보유 주식량보다 많이 팔때
        log.info(stockService.setSellOrder("abc", 1650, 222, 3));
        //발행잔량 남아있을때
        log.info(stockService.setSellOrder("abc", 1000, 2, 2));
    }
    @Test
    void setBuyOrderTest(){
        //NO
        //없는 학생아이디로 매수요청(보유포인트 체크 부분에서 BindingException 발생 리턴타입 int가 null을 받지못해 발생)
        Assertions.assertThrows(BindingException.class, () -> stockService.setBuyOrder("testid1111", 800, 2, 122));
        //잘못된 주식번호로 매수요청(발행개수 체크 부분에서 NullPointExcepion 발생)
        Assertions.assertThrows(NullPointerException.class, () -> stockService.setBuyOrder("abc", 800, 2, 122));
        //YES
        //학생간거래 매칭실패 주문대기
        log.info(stockService.setBuyOrder("abc", 1, 4, 1));
        //학생간거래 매칭완료
        log.info(stockService.setBuyOrder("abc", 800, 2, 1));
        //보유포인트 부족
        log.info(stockService.setBuyOrder("abc", 800222, 2, 1));
        //발행잔량과 거래
        log.info(stockService.setBuyOrder("abc", 2100, 1, 2));
    }
    @Test
    void getStockNameTest(){
        //NO
        //없는 주식번호(null 반환)
        Assertions.assertEquals(null,stockService.getStockName(111));
        //YES
        log.info("주식명 : " + stockService.getStockName(1));
    }
    @Test
    void getStockNameListTest(){
        //NO
        //입력값 존재x
        //YES
        log.info("주식명 리스트 : " + stockService.getStockNameList());
    }
    @Test
    void getStockInfoTest(){
        //NO
        //없는 주식번호 조회([]반환)
        Assertions.assertEquals(0,stockService.getStockInfo(111).size());
        Assertions.assertFalse(stockService.getStockInfo(111).size() > 0);
        //YES
        log.info("주식 기본 정보 : " + stockService.getStockInfo(1));
    }
    @Test
    void getStockPrice(){
        //NO
        //존재하지 않는 주식번호(null반환을 stocks에서 받아 null로 값을 조회 해서 NullPointerException예외발생)
        Assertions.assertThrows(NullPointerException.class, () -> stockService.getStockPrice(111));
        //YES
        log.info("주식 현재 가격 : " + stockService.getStockPrice(1));
    }
    @Test
    void getStockPriceChangeTest(){
        //NO
        //존재하지 않는 주식번호(null반환 리턴타입 int가 null을 받지 못해 BindingException예외발생)
        Assertions.assertThrows(BindingException.class, () -> stockService.getStockPriceChange(111));
        //YES
        log.info("주식 이전가 대비 가격 : " + stockService.getStockPriceChange(1));
    }
    @Test
    void getChangeRateTest(){
        //NO
        //존재하지 않는 주식번호(null반환 리턴타입 int가 null을 받지 못해 BindingException예외발생)
        Assertions.assertThrows(BindingException.class, () -> stockService.getChangeRate(111));
        //YES
        log.info("주식 등락률 : " + stockService.getChangeRate(1) + "%");
    }
    @Test
    void getPrevPointTest(){
        //NO
        //존재하지 않는 주식번호(null반환 리턴타입 int가 null을 받지 못해 BindingException예외발생)
        Assertions.assertThrows(BindingException.class, () -> stockService.getPrevPoint(111));
        //YES
        log.info("주식 이전가격 : " + stockService.getPrevPoint(1));
    }
    @Test
    void setStockPubBalanceTest(){
        //NO
        //없는 주식번호
        Assertions.assertEquals(false ,stockService.setStockPubBalance(1,1111));
        //DB 컬럼이 받을수 있는 범위 초과(DataIntegerityViolationException 발생)
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> stockService.setStockPubBalance(10000000, 1));
        //YES
        //발행잔량 보다 넘게 차감시 -값 들어감 체크해야할듯
        Assertions.assertEquals(true, stockService.setStockPubBalance(1000, 1));
        log.info("발행 개수 변경 결과 : " + stockService.setStockPubBalance(1,1 ));
    }
    @Test
    void getStockPubInfoTest(){
        //NO
        //없는 주식번호(null반환)
        Assertions.assertEquals(null, stockService.getStockPubInfo(111));
        Assertions.assertEquals(null, stockService.getStockPubInfo(111111));
        //YES
        log.info("발행 정보 :" + stockService.getStockPubInfo(1));
    }

}
