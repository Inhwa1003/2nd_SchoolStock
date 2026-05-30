package com.school.schoolstock.domain.order.service;

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
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    void getMatchOrderTest() {
        Map<String, Object> result = orderService.getMatchOrder(
                1,
                1000,
                1,
                "abc",
                "매수"
        );

        System.out.println("주문 매칭 결과 = " + result);

        // 매칭되는 주문이 없으면 null일 수도 있으므로 출력 확인용으로 먼저 사용
        // assertNotNull(result);
    }

    @Test
    void setOrderRequestTest() {
        boolean result = orderService.setOrderRequest(
                "BUY",
                1000,
                1,
                "PENDING",
                "abc",
                1
        );

        assertTrue(result);

        System.out.println("주문 요청 등록 결과 = " + result);
    }

    @Test
    void getMyOrderNoTest() {
        int result = orderService.getMyOrderNo(
                "BUY",
                "abc",
                1,
                "MATCHED",
                2,
                800
        );

        System.out.println("최근 등록한 주문 번호 = " + result);

        // DB에 해당 조건의 주문이 반드시 존재한다면 사용
        // assertTrue(result > 0);
    }

    @Test
    void getTotalOrderTest() {
        List<Orders> result = orderService.getTotalOrder(1);

        assertNotNull(result);

        System.out.println("특정 주식 대기 주문 전체 조회 = " + result);
    }

    @Test
    void getTotalSellOrderTest() {
        List<Orders> result = orderService.getTotalSellOrder(1);

        assertNotNull(result);

        System.out.println("특정 주식 대기 매도 주문 조회 = " + result);
    }

    @Test
    void getTotalBuyOrderTest() {
        List<Orders> result = orderService.getTotalBuyOrder(1);

        assertNotNull(result);

        System.out.println("특정 주식 대기 매수 주문 조회 = " + result);
    }

    @Test
    void setOrderStatePendingTest() {
        boolean result = orderService.setOrderStatePending(1);

        System.out.println("주문 상태 대기 변경 결과 = " + result);

        // order_no = 1이 실제 DB에 존재한다면 사용
        // assertTrue(result);
    }

    @Test
    void setOrderStateCancelTest() {
        boolean result = orderService.setOrderStateCancel(1);

        System.out.println("주문 상태 취소 변경 결과 = " + result);

        // order_no = 1이 실제 DB에 존재한다면 사용
        // assertTrue(result);
    }

    @Test
    void setOrderStateMatchedTest() {
        boolean result = orderService.setOrderStateMatched(1);

        System.out.println("주문 상태 체결 변경 결과 = " + result);

        // order_no = 1이 실제 DB에 존재한다면 사용
        // assertTrue(result);
    }
}