package com.school.schoolstock.domain.teacher.service;

import com.school.schoolstock.domain.coupon.dto.CouponUpdateRequest;
import com.school.schoolstock.domain.student.repository.StudentRepository;
import com.school.schoolstock.domain.teacher.dto.request.PointGrantRequest;
import com.school.schoolstock.domain.teacher.dto.response.StudentListResponse;
import com.school.schoolstock.domain.coupon.repository.CouponRepository;
import com.school.schoolstock.global.error.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@SpringBootTest
public class TeacherServiceTest {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    public void getMyStudentsListTest() {
        // NO
        // 존재하지 않는 선생님 아이디
        Assertions.assertTrue(teacherService.getMyStudentsList("testTeacher").isEmpty());

        // YES
        // users + teachers에 존재하는 선생님 아이디
        List<StudentListResponse> result = teacherService.getMyStudentsList("t0");

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());

        log.info("담당 반 학생 목록 조회 결과 : {}", result);
    }

    @Test
    public void getTeacherNameTest() {
        String teacherId = "t0";

        // NO
        // 존재하지 않는 선생님 -> null
        Assertions.assertNull(teacherService.getTeacherName("testTeacher"));

        // YES
        // 존재하는 선생님 이름
        String name = teacherService.getTeacherName(teacherId);
        Assertions.assertNotNull(name);
        Assertions.assertNotEquals(teacherId, name);
        log.info("선생님 이름 = {}", name);
    }

    @Test
    public void getStudentIdInClassTest() {
        String teacherId = "t0";

        // 그 선생 반 실제 학생 번호 하나 확보
        List<StudentListResponse> students = teacherService.getMyStudentsList(teacherId);
        Assertions.assertFalse(students.isEmpty());
        int existingNumber = students.get(0).getStudentNumber();

        // YES
        // 내 반 학생 번호 -> studentId 반환
        Assertions.assertNotNull(teacherService.getStudentIdInClass(teacherId, existingNumber));
        log.info("번호 " + existingNumber + " -> studentId : "
                + teacherService.getStudentIdInClass(teacherId, existingNumber));

        // NO
        // 내 반에 없는 번호 -> null
        Assertions.assertThrows(BusinessException.class, () -> teacherService.getStudentIdInClass(teacherId, 99999));
    }

    @Test
    public void givePointTest() {
        String teacherId = "t0";

        // 그 선생 반 실제 학생 한 명
        List<StudentListResponse> students = teacherService.getMyStudentsList(teacherId);
        Assertions.assertFalse(students.isEmpty());
        String studentId = teacherService.getStudentIdInClass(teacherId, students.get(1).getStudentNumber());

        int beforeHistory = studentRepository.getMyPointHistoryList(studentId).size();
        int beforePoint   = studentRepository.getMyPoint(studentId);

        // YES
        // 지급 -> 보유포인트 증가 + 내역 1건 추가
        teacherService.givePoint(teacherId, 63, PointGrantRequest.builder()
                .points(100)
                .content("분리수거 지급").build());
        Assertions.assertEquals(beforeHistory + 1, studentRepository.getMyPointHistoryList(studentId).size());
        Assertions.assertEquals(beforePoint + 100, studentRepository.getMyPoint(studentId));   // getMyPoint가 total_point면 통과

        log.info("지급 후 보유포인트 : " + studentRepository.getMyPoint(studentId));
    }
    @Test
    public void setCouponTest() {
        // NO 1
        // 쿠폰명이 비어있는 경우
        Assertions.assertThrows(BusinessException.class, () -> teacherService.setCoupon(CouponUpdateRequest.builder()
                .couponNo(3)
                .name("")
                .couponPoint(300).build()));


        // NO 2
        // 쿠폰 포인트가 0 이하인 경우
        Assertions.assertThrows(BusinessException.class, () -> teacherService.setCoupon(CouponUpdateRequest.builder()
                .couponNo(3)
                .name("수정된 쿠폰명")
                .couponPoint(0).build()));


        // NO 3
        // 존재하지 않는 쿠폰 번호
        Assertions.assertThrows(BusinessException.class, () -> teacherService.setCoupon(CouponUpdateRequest.builder()
                .couponNo(99999)
                .name("없는 쿠폰")
                .couponPoint(300).build()));


        // YES
        // 존재하는 쿠폰 번호 수정
        Assertions.assertDoesNotThrow(() -> teacherService.setCoupon(CouponUpdateRequest.builder()
                .couponNo(3)
                .name("수정된 쿠폰명")
                .couponPoint(2000).build()));
    }

    @Test
    public void deleteCouponTest() {
        // NO 1
        // 쿠폰 번호가 0 이하인 경우
        Assertions.assertThrows(BusinessException.class, () -> teacherService.deleteCoupon(0));


        // NO 2
        // 존재하지 않는 쿠폰 번호
        Assertions.assertThrows(BusinessException.class, () -> teacherService.deleteCoupon(99999));


        // YES
        // 존재하는 쿠폰 번호 삭제(학생이 보유하지 않아야만 삭제가능)
        int couponNo = 4;
        //Assertions.assertDoesNotThrow(() -> teacherService.deleteCoupon(couponNo));
        Assertions.assertNull(couponRepository.getCoupon(couponNo));
    }

}