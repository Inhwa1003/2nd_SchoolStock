package com.school.schoolstock.domain.order.service;

import com.school.schoolstock.domain.order.dto.request.BuySellOrderRequest;
import com.school.schoolstock.domain.order.dto.response.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    void getStockOrdersTest() {
        // YES — 종목3은 PENDING 매수/매도 주문 있음
        List<OrderResponse> sell = orderService.getStockOrders(3, "SELL");
        List<OrderResponse> buy  = orderService.getStockOrders(3, "BUY");
        log.info("주식번호 3번 매도목록: {}", sell);
        log.info("주식번호 3번 매수목록: {}", buy);
        Assertions.assertNotNull(sell);
        Assertions.assertNotNull(buy);

        // NO — 대기주문 없는 종목 -> 빈 리스트
        Assertions.assertTrue(orderService.getStockOrders(2, "SELL").isEmpty());
    }

    @Test
    void setSellOrderTest() {
        // NO — 없는 학생: 보유수량 0 → "보유 주식량보다 많은..." 반환
        log.info(orderService.setSellOrder("testid1111",
                BuySellOrderRequest.builder().stockNo(1).orderPoint(1000).orderAmount(1).build()));

        // NO — 없는 종목: getStockPubInfo null -> NullPointerException
        Assertions.assertThrows(NullPointerException.class, () ->
                orderService.setSellOrder("abc", BuySellOrderRequest.builder().stockNo(1111).orderPoint(1000).orderAmount(2).build()));

        // YES — abc 매도 (매칭/대기 결과 로그로 확인)
        log.info(orderService.setSellOrder("abc", BuySellOrderRequest.builder().stockNo(1).orderPoint(11111).orderAmount(2).build()));
    }

    @Test
    void setBuyOrderTest() {
        // NO — 없는 학생: getMyPoint(int)가 null 못 받아 BindingException
        Assertions.assertThrows(BindingException.class, () ->
                orderService.setBuyOrder("testid1111", BuySellOrderRequest.builder().stockNo(1).orderPoint(800).orderAmount(2).build()));

        // NO — 잘못된 종목: getStockPubInfo null → NullPointerException
        Assertions.assertThrows(NullPointerException.class, () ->
                orderService.setBuyOrder("abc", BuySellOrderRequest.builder().stockNo(1111).orderPoint(800).orderAmount(2).build()));

        // YES — abc 매수 (대기/매칭 결과 로그)
        log.info(orderService.setBuyOrder("abc", BuySellOrderRequest.builder().stockNo(1).orderPoint(1).orderAmount(4).build()));
    }
}