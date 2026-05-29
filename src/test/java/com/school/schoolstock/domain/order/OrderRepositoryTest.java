package com.school.schoolstock.domain.order;

import com.school.schoolstock.domain.order.repository.OrderRepository;
import com.school.schoolstock.domain.order.vo.Orders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    void getMatchOrder_test() {
        // given
        int stockNo = 3;
        int orderPrice = 1600;
        int orderAmount = 5;
        String studentId = "abc";
        String content = "SELL";

        // when
        Map<String, Object> result =
                orderRepository.getMatchOrder(stockNo, orderPrice, orderAmount, studentId, content);

        // then
        System.out.println("주문 매칭 조회 결과: " + result);

        assertNotNull(result);
        assertEquals(4, ((Number) result.get("orderNo")).intValue());
        assertEquals("SELL", result.get("orderContent"));
        assertEquals(1600, ((Number) result.get("orderPoint")).intValue());
        assertEquals(5, ((Number) result.get("amount")).intValue());
        assertEquals("PENDING", result.get("orderState"));
        assertEquals("def", result.get("studentId"));
        assertEquals(3, ((Number) result.get("stockNo")).intValue());
    }

    @Test
    void setOrderRequest_test() {
        // given
        String content = "SELL";
        int price = 1400;
        int amount = 2;
        String state = "PENDING";
        String studentId = "def";
        int stockNo = 1;

        // when
        boolean result =
                orderRepository.setOrderRequest(content, price, amount, state, studentId, stockNo);

        // then
        System.out.println("주문 요청 결과: " + result);
        assertTrue(result);
    }

    @Test
    void getMyOrderNo_test() {
        // given
        String content = "BUY";
        String studentId = "abc";
        int stockNo = 1;
        String state = "MATCHED";
        int amount = 2;
        int price = 800;

        // when
        int orderNo =
                orderRepository.getMyOrderNo(content, studentId, stockNo, state, amount, price);

        // then
        System.out.println("최근 등록한 주문 번호: " + orderNo);
        assertEquals(1, orderNo);
    }

    @Test
    void getTotalOrder_test() {
        // given
        int stockNo = 3;

        // when
        List<Orders> result = orderRepository.getTotalOrder(stockNo);

        // then
        System.out.println("특정 주식 대기 주문 전체 조회 결과: " + result);

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void getTotalSellOrder_test() {
        // given
        int stockNo = 3;

        // when
        List<Orders> result = orderRepository.getTotalSellOrder(stockNo);

        // then
        System.out.println("특정 주식 대기 매도 주문 조회 결과: " + result);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("SELL", result.get(0).getOrderContent());
        assertEquals(1600, result.get(0).getOrderPoint());
        assertEquals(5, result.get(0).getAmount());
    }

    @Test
    void getTotalBuyOrder_test() {
        // given
        int stockNo = 1;

        // when
        List<Orders> result = orderRepository.getTotalBuyOrder(stockNo);

        // then
        System.out.println("특정 주식 대기 매수 주문 조회 결과: " + result);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("BUY", result.get(0).getOrderContent());
        assertEquals(800, result.get(0).getOrderPoint());
        assertEquals(2, result.get(0).getAmount());
    }

    @Test
    void setOrderStatePending_test() {
        // given
        int orderNo = 7;

        // when
        boolean result = orderRepository.setOrderStatePending(orderNo);

        // then
        System.out.println("주문 상태 대기 변경 결과: " + result);
        assertTrue(result);
    }

    @Test
    void setOrderStateCancel_test() {
        // given
        int orderNo = 3;

        // when
        boolean result = orderRepository.setOrderStateCancel(orderNo);

        // then
        System.out.println("주문 상태 취소 변경 결과: " + result);
        assertTrue(result);
    }

    @Test
    void setOrderStateMatched_test() {
        // given
        int orderNo = 3;

        // when
        boolean result = orderRepository.setOrderStateMatched(orderNo);

        // then
        System.out.println("주문 상태 체결 변경 결과: " + result);
        assertTrue(result);
    }

    @Test
    void setOrderStatePending_fail_notExistOrderNo_test() {
        // given
        int notExistOrderNo = 99999;

        // when
        boolean result = orderRepository.setOrderStatePending(notExistOrderNo);

        // then
        System.out.println("존재하지 않는 주문 상태 변경 결과: " + result);
        assertFalse(result);
    }
}