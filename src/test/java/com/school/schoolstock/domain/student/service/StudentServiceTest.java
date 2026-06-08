package com.school.schoolstock.domain.student.service;

import com.school.schoolstock.domain.student.dto.response.MyAssetResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional   // 각 테스트 후 자동 롤백 (쓰기 테스트해도 DB 원복)
@Slf4j
@SpringBootTest  // 스프링 컨텍스트 전체 + 실제 DB (통합 테스트)
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    void getMyAssetTest() {
        // YES — 존재하는 학생
        MyAssetResponse asset = studentService.getMyAsset("abc");
        Assertions.assertNotNull(asset);
        Assertions.assertFalse(asset.getMyStocks().isEmpty());
        log.info("총자산:{}, 가용포인트:{}, 총손익:{}, 보유쿠폰:{}, 보유종목수:{}",
                asset.getTotalValue(), asset.getMyPoint(), asset.getTotalProfit(),
                asset.getCouponAmount(), asset.getMyStocks().size());

        // NO — 없는 학생 → getMyValue(int)가 null 못 받아 BindingException
        Assertions.assertThrows(BindingException.class,
                () -> studentService.getMyAsset("testid1111"));
    }

    @Test
    void getMyOrderTest() {
        // YES
        Assertions.assertFalse(studentService.getMyOrder("abc", 3).isEmpty());
        log.info("내 대기주문: {}", studentService.getMyOrder("abc", 3));

        // NO — 없는 아이디 → 빈 리스트
        Assertions.assertTrue(studentService.getMyOrder("testid1111", 3).isEmpty());
    }

    @Test
    void getMyPointHistoryTest() {
        // YES
        Assertions.assertFalse(studentService.getMyPointHistory("abc").isEmpty());
        log.info("포인트 내역: {}", studentService.getMyPointHistory("abc"));

        // NO — 없는 아이디 → 빈 리스트
        Assertions.assertTrue(studentService.getMyPointHistory("testid1111").isEmpty());
    }

    @Test
    void getMyCouponTest() {
        // YES
        Assertions.assertFalse(studentService.getMyCoupon("abc").isEmpty());
        log.info("보유 쿠폰: {}", studentService.getMyCoupon("abc"));

        // NO — 없는 아이디 → 빈 리스트
        Assertions.assertTrue(studentService.getMyCoupon("testid1111").isEmpty());
    }

    @Test
    void setStudentPointUpTest() {
        // YES
        Assertions.assertTrue(studentService.setStudentPointUp("abc", 100));

        // NO — 없는 아이디 → 갱신 0건 → false
        Assertions.assertFalse(studentService.setStudentPointUp("testid1111", 100));
    }

    @Test
    void setMyOrderCancelTest() {
        // YES — abc의 PENDING 주문 #3 취소 (테스트 후 롤백)
        Assertions.assertTrue(studentService.setMyOrderCancel(3));

        // NO — 없는 주문번호 → false
        Assertions.assertFalse(studentService.setMyOrderCancel(999999));
    }
}
