package com.school.schoolstock.domain.order;

import com.school.schoolstock.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void findLatestOrderNoTest() {
        Integer orderNo = orderRepository.findLatestOrderNo(
                "BUY",
                "abc",
                1,
                "MATCHED",
                2,
                800
        );
        assertThat(orderNo).isNotNull();
        assertThat(orderNo).isEqualTo(1);
    }
}
