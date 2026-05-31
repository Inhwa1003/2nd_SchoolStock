package com.school.schoolstock.domain.student.service;


import com.school.schoolstock.domain.student.service.StudentServiceImpl;
import com.school.schoolstock.domain.student.vo.Students;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
@SpringBootTest
public class StudentServiceTest {
    @Autowired
    private StudentServiceImpl studentService;
    @Test
    public void getIdCheckTest(){
        //NO
        //존재 하는 아이디
        Assertions.assertEquals("abc",studentService.getIdCheck("abc"));
        Assertions.assertEquals("def",studentService.getIdCheck("def"));
        //YES
        // 존재하지 않는 아이디 (null 반환)
        log.info(studentService.getIdCheck("testid"));
    }
    @Test
    public void setMemberTest(){
        //NO
        //존재하는 아이디 입력(DuplicateKeyException 발생)
        Assertions.assertThrows(DuplicateKeyException.class, () -> studentService.setMember(Students.builder()
                .studentId("abc")
                .password("1234")
                .name("최동석")
                .grade(5)
                .className("5")
                .studentNumber(44)
                .build()));
        //YES
        log.info("회원가입 결과 : " + studentService.setMember(Students.builder()
                .studentId("choi")
                .password("1234")
                .name("최동석")
                .grade(5)
                .className("5")
                .studentNumber(44)
                .build()));
    }
    @Test
    public void loginTest(){
        //NO
        //비밀번호 틀림
        Assertions.assertNull(studentService.login("def", "22222"));
        Assertions.assertNull(studentService.login("abc", "111111"));
        //없는 아이디 로그인
        Assertions.assertTrue(studentService.login("testid2222", "abc123!") == null);
        Assertions.assertNull(studentService.login("testid1111", "abc123!"));
        //YES
        log.info(studentService.login("abc", "abc123!").toString());
    }
    @Test
    public void getMyValueTest(){
        //NO
        //존재하지 않는 아이디(BindingException발생 null 반환이 리턴타입 int가 받지 못해서 발생)
        Assertions.assertThrows(BindingException.class, () -> studentService.getMyValue("testid1111"));
        //YES
        log.info("내 총자산 : " + studentService.getMyValue("abc"));
    }
    @Test
    public void getMyPointTest(){
        //NO
        //존재하지 않는 아이디(BindingException발생 null 반환이 리턴타입 int가 받지 못해서 발생)
        Assertions.assertThrows(BindingException.class, () -> studentService.getMyPoint("testid1111"));
        //YES
        log.info("내 가용포인트 : " + studentService.getMyPoint("abc"));
    }
    @Test
    public void getTotalProfitTest(){
        //NO
        //존재하지 않는 아이디(총 손익이 존재 하지 않는 사람과 같은 0 반환 아이디중복체크 업무 들어가면 좋을듯)
        Assertions.assertEquals(0, studentService.getTotalProfit("testid1111"));
        //YES
        log.info("총 손익 : " + studentService.getTotalProfit("abca"));
    }
    @Test
    public void getMyCouponAmountTest(){
        //NO
        //존재하지 않는 아이디(보유 쿠폰이 존재 하지 않는 사람과 같은 0 반환 아이디중복체크 업무 들어가면 좋을듯)
        Assertions.assertEquals(0, studentService.getMyCouponAmount("testid1111"));
        //YES
        log.info("보유쿠폰 수량 : " + studentService.getMyCouponAmount("abc"));
    }
    @Test
    public void getMyStockAmountTest(){
        //NO
        //존재하지 않는 아이디, 존재하지 않는 주식번호(쿼리문 결과로 0을 반환해버림)
        Assertions.assertEquals(0,studentService.getMyStockAmount("testid1111", 44));
        //존재하지 않는 주식번호(쿼리문 결과로 0을 반환해버림)
        Assertions.assertTrue(studentService.getMyStockAmount("abc", 44) == 0);
        //존재하지 않는 아이디(쿼리문 결과로 0을 반환해버림)
        Assertions.assertTrue(studentService.getMyStockAmount("testid1111", 1) == 0);
        //YES
        log.info("특정 보유주식 수량 : " + studentService.getMyStockAmount("abc", 1));
    }
    @Test
    public void getAveragePointTest(){
        //NO
        //존재하지 않는 아이디, 존재하지 않는 주식번호(쿼리문 결과로 0을 반환해버림)
        Assertions.assertTrue(studentService.getAveragePoint("testid1111", 111) == 0);
        //존재하지 않는 주식번호(쿼리문 결과로 0을 반환해버림)
        Assertions.assertTrue(studentService.getAveragePoint("def", 133) == 0);
        //존재하지 않는 아이디(쿼리문 결과로 0을 반환해버림)
        Assertions.assertTrue(studentService.getAveragePoint("testid1111", 1) == 0);
        //YES
        log.info("특정 보유 주식 평균가격 : " + studentService.getAveragePoint("abc", 1));
    }
    @Test
    public void getPurchasePointTest(){
        //NO
        //존재하지 않는 아이디, 존재하지 않는 주식번호(쿼리문 결과로 0을 반환해버림)
        Assertions.assertTrue(studentService.getPurchasePoint("testid1111", 133) == 0);
        //존재하지 않는 주식번호(쿼리문 결과로 0을 반환해버림)
        Assertions.assertTrue(studentService.getPurchasePoint("def", 155) == 0);
        //존재하지 않는 아이디(쿼리문 결과로 0을 반환해버림)
        Assertions.assertTrue(studentService.getPurchasePoint("testid1111", 1) == 0);
        //YES
        log.info("특정 보유 주식 총 구매 비용 : " + studentService.getPurchasePoint("abc", 1));
    }
    @Test
    public void getMyStockNosTest(){
        //NO
        //존재하지 않은 아이디(빈 리스트 반환)
        Assertions.assertTrue(studentService.getMyStockNos("testid1111").size() == 0);
        Assertions.assertFalse(studentService.getMyStockNos("testid1111").size() > 0);
        Assertions.assertTrue(studentService.getMyStockNos("testid1111").isEmpty());
        //YES
        log.info("학생이 구매한 주식 번호 : " + studentService.getMyStockNos("abc"));
    }
    @Test
    public void getStockProfitTest(){
        //NO
        //없는 주식번호, 아이디로 조회(BindingException 발생 리턴타입 int가 null반환을 받지못해 발생)
        Assertions.assertThrows(BindingException.class, () -> studentService.getStockProfit("testid1111", 111));
        //없는 아이디로 조회(BindingException 발생 리턴타입 int가 null반환을 받지못해 발생)
        Assertions.assertThrows(BindingException.class, () -> studentService.getStockProfit("testid1111", 1));
        //없는 주식번호로 조회(BindingException 발생 리턴타입 int가 null반환을 받지못해 발생)
        Assertions.assertThrows(BindingException.class, () -> studentService.getStockProfit("abc", 111));
        //YES
        log.info("특정 보유 주식 수익금 : " + studentService.getStockProfit("abc", 1));
    }
    @Test
    public void getTotalMyOrderTest(){
        //NO
        //없는 아이디, 주식번호로 조회(없는 주문과 같은 [] 반환하기때문에 service에서 아이디 중복체크 업무 들어가면 좋을것같음)
        Assertions.assertTrue(studentService.getTotalMyOrder("testid1111", 44).size() == 0);
        //없는 주식번호로 조회(없는 주문과 같은 [] 반환하기때문에 service에서 아이디 중복체크 업무 들어가면 좋을것같음)
        Assertions.assertTrue(studentService.getTotalMyOrder("abc", 44).isEmpty());
        //없는 아이디로 조회(없는 주문과 같은 [] 반환하기때문에 service에서 아이디 중복체크 업무 들어가면 좋을것같음)
        Assertions.assertTrue(studentService.getTotalMyOrder("testid1111", 3).isEmpty());
        //YES
        log.info("특정 주식 내 주문 요청 : " + studentService.getTotalMyOrder("abc", 3));
        //없는 결과 [] 반환
        log.info("특정 주식 내 주문 요청 : " + studentService.getTotalMyOrder("abc", 1));
    }
    @Test
    public void setStudentPointUpTest(){
        //NO
        //없는 아이디 포인트 증가
        Assertions.assertTrue(!studentService.setStudentPointDown("testid1122", 100));
        Assertions.assertFalse(studentService.setStudentPointDown("testid1111", 100));
        //YES
        log.info("보유 포인트 증가 결과 : " + studentService.setStudentPointDown("abc", 100));
    }
    @Test
    public void getMyPointHistoryListTest(){
        //NO
        //없는 아이디(내역이 없는 사람과 같은 []반환 service에서 아이디 중복 체크 들어가면 좋을듯)
        Assertions.assertTrue(studentService.getMyPointHistoryList("testid11111").size() == 0);
        Assertions.assertFalse(studentService.getMyPointHistoryList("testid11111").size() > 0);
        Assertions.assertTrue(studentService.getMyPointHistoryList("testid11111").isEmpty());
        //YES
        log.info("내 포인트 내역 정보 (쿠폰, 지급, 주식) : " + studentService.getMyPointHistoryList("abc"));
    }
    @Test
    public void setStudentAssetsTest(){
        //NO
        //없는 아이디(false 반환)
        Assertions.assertEquals(false,studentService.setStudentAssets("testid1111", 100));
        Assertions.assertTrue(!studentService.setStudentAssets("testid1111", 100));
        Assertions.assertFalse(studentService.setStudentAssets("testid1111", 100));
        //YES
        //-값 들어가면 차감으로 동작 음수값 체크필요
        Assertions.assertTrue(studentService.setStudentAssets("abc", -100));
        log.info("학생 포인트 차감 및 보유쿠폰 수량 증가 결과 : " + studentService.setStudentAssets("abc", 100));
    }
    @Test
    public void getMyCouponListTest(){
        //NO
        //없는 아이디(쿠폰을 보유하지 않은 학생과 같은 []반환 service에서 아이디 중복체크 업무 들어가면 좋을듯
        Assertions.assertFalse(studentService.getMyCouponList("testid1111").size() > 0);
        Assertions.assertEquals(0,studentService.getMyCouponList("testid1111").size());
        Assertions.assertTrue(studentService.getMyCouponList("testid1111").isEmpty());
        //YES
        log.info("보유 쿠폰 정보 : " + studentService.getMyCouponList("abc"));
    }
}
