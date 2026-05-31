package com.school.schoolstock.domain.student.repository;

import com.school.schoolstock.domain.student.vo.Students;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;
    @Test
    public void getIdCheckTest(){
        System.out.println(studentRepository.getIdCheck("abc"));
    }

    @Test
    public void setMemberTest(){
        boolean flag = studentRepository.setMember(Students.builder()
                .studentId("choi")
                .password("1234")
                .studentNumber(44)
                .name("최동석")
                .className("5")
                .grade(6).build());
        System.out.println(flag);
    }
    @Test
    public void loginTest(){
        System.out.println(studentRepository.login("abc","abc123!").toString());
    }
    @Test
    public void getMyValueTest(){
        System.out.println(studentRepository.getMyValue("abc"));
    }
    @Test
    public void getMyPointTest(){
        System.out.println(studentRepository.getMyPoint("abc"));
    }
    @Test
    public void getTotalProfitTest(){
        System.out.println(studentRepository.getTotalProfit("abc"));
    }
    @Test
    public void getMyCouponAmountTest(){
        System.out.println(studentRepository.getMyCouponAmount("abc"));
    }
    @Test
    public void getMyStockAmountTest(){
        System.out.println(studentRepository.getMyStockAmount("abc",1));
    }
    @Test
    public void getAveragePointTest(){
        System.out.println(studentRepository.getAveragePoint("abc",1));
    }
    @Test
    public void getPurchasePointTest(){
        System.out.println(studentRepository.getPurchasePoint("abc",1));
    }
    @Test
    public void getMyStockNosTest(){
        System.out.println(studentRepository.getMyStockNos("abc").toString());
    }
    @Test
    public void getStockProfitTest(){
        System.out.println(studentRepository.getStockProfit("abc", 1));
    }
    @Test
    public void getTotalMyOrderTest(){
        System.out.println(studentRepository.getTotalMyOrder("abc", 3));
    }
    @Test
    public void setStudentPointUpTest(){
        System.out.println(studentRepository.setStudentPointUp("abc", 100));
    }
    @Test
    public void setStudentPointDownTest(){
        System.out.println(studentRepository.setStudentPointDown("abc", 100));
    }
    @Test
    public void getMyPointHistoryListTest(){
        System.out.println(studentRepository.getMyPointHistoryList("abc"));
    }
    @Test
    public void setStudentAssetsTest(){
        System.out.println(studentRepository.setStudentAssets("abc",100));
    }
    @Test
    public void getMyCouponListTest(){
        System.out.println(studentRepository.getMyCouponList("abc"));
    }
}
