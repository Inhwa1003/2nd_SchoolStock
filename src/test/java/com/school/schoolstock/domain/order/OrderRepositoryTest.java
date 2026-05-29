package com.school.schoolstock.domain.order;

import com.school.schoolstock.domain.order.repository.OrderRepository;
import com.school.schoolstock.domain.order.vo.Orders;
import org.apache.ibatis.binding.BindingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void getMatchOrder_test() {
        Map<String, Object> result = orderRepository.getMatchOrder(
                3,
                1600,
                5,
                "abc",
                "SELL"
        );

        System.out.println("주문 매칭 결과: " + result);

        assertNotNull(result);
        assertEquals("SELL", result.get("orderContent"));
        assertEquals("PENDING", result.get("orderState"));
        assertEquals("def", result.get("studentId"));
    }

    @Test
    void setOrderRequest_test() {
        boolean result = orderRepository.setOrderRequest(
                "SELL",
                1400,
                2,
                "PENDING",
                "def",
                1
        );

        System.out.println("주문 요청 결과: " + result);

        assertTrue(result);
    }

    @Test
    void getMyOrderNo_test() {
        assertThrows(BindingException.class, () -> {
            int result = orderRepository.getMyOrderNo(
                    "BUY",
                    "abc",
                    1,
                    "MATCHED",
                    10,
                    1050
            );

            System.out.println("최근 등록한 주문 번호: " + result);
        });
    }

    @Test
    void getTotalOrder_test() {
        List<Orders> result = orderRepository.getTotalOrder(1);

        System.out.println("특정 주식 대기 주문 전체 조회 결과: " + result);

        assertNotNull(result);
        assertFalse(result.isEmpty());

        Orders order = result.get(0);

        assertEquals("BUY", order.getOrderContent());
        assertEquals(800, order.getOrderPoint());
        assertEquals(2, order.getAmount());
    }

    @Test
    void getTotalSellOrder_test() {
        List<Orders> result = orderRepository.getTotalSellOrder(3);

        System.out.println("특정 주식 대기 매도 주문 조회 결과: " + result);

        assertNotNull(result);
        assertFalse(result.isEmpty());

        Orders order = result.get(0);

        assertEquals("SELL", order.getOrderContent());
        assertEquals(1600, order.getOrderPoint());
        assertEquals(5, order.getAmount());
    }

    @Test
    void getTotalBuyOrder_test() {
        List<Orders> result = orderRepository.getTotalBuyOrder(1);

        System.out.println("특정 주식 대기 매수 주문 조회 결과: " + result);

        assertNotNull(result);
        assertFalse(result.isEmpty());

        Orders order = result.get(0);

        assertEquals("BUY", order.getOrderContent());
        assertEquals(800, order.getOrderPoint());
        assertEquals(2, order.getAmount());
    }

    @Test
    void setOrderStatePending_test() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            boolean result = orderRepository.setOrderStatePending(1);
            System.out.println("주문 상태 대기 변경 결과: " + result);
        });
    }

    @Test
    void setOrderStateCancel_test() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            boolean result = orderRepository.setOrderStateCancel(1);
            System.out.println("주문 상태 취소 변경 결과: " + result);
        });
    }

    @Test
    void setOrderStateMatched_test() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            boolean result = orderRepository.setOrderStateMatched(1);
            System.out.println("주문 상태 체결 변경 결과: " + result);
        });
    }
}