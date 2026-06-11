package com.school.schoolstock.domain.teacher.service;

import com.school.schoolstock.domain.student.repository.StudentRepository;
import com.school.schoolstock.domain.teacher.dto.response.StudentListResponse;
import com.school.schoolstock.domain.coupon.repository.CouponRepository;
import com.school.schoolstock.domain.coupon.vo.Coupons;
//import com.school.schoolstock.domain.teacher.dto.StudentListResponse;
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
        Assertions.assertNull(teacherService.getStudentIdInClass(teacherId, 99999));
    }

    @Test
    public void givePointTest() {
        String teacherId = "t0";
        int amount = 3000;

        // 그 선생 반 실제 학생 한 명
        List<StudentListResponse> students = teacherService.getMyStudentsList(teacherId);
        Assertions.assertFalse(students.isEmpty());
        String studentId = teacherService.getStudentIdInClass(teacherId, students.get(0).getStudentNumber());

        int beforeHistory = studentRepository.getMyPointHistoryList(studentId).size();
        int beforePoint   = studentRepository.getMyPoint(studentId);

        // YES
        // 지급 -> 보유포인트 증가 + 내역 1건 추가
        Assertions.assertTrue(teacherService.givePoint(studentId, amount, "테스트 지급"));
        Assertions.assertEquals(beforeHistory + 1, studentRepository.getMyPointHistoryList(studentId).size());
        Assertions.assertEquals(beforePoint + amount, studentRepository.getMyPoint(studentId));   // getMyPoint가 total_point면 통과

        log.info("지급 후 보유포인트 : " + studentRepository.getMyPoint(studentId));
    }
    public void setCouponTest() {
        // NO 1
        // 쿠폰명이 비어있는 경우
        Coupons emptyNameCoupon = new Coupons();
        emptyNameCoupon.setCouponNo(3);
        emptyNameCoupon.setName("");
        emptyNameCoupon.setCouponPoint(300);

        String emptyNameResult = teacherService.setCoupon(emptyNameCoupon);

        Assertions.assertEquals("쿠폰명을 입력해주세요.", emptyNameResult);


        // NO 2
        // 쿠폰 포인트가 0 이하인 경우
        Coupons invalidPointCoupon = new Coupons();
        invalidPointCoupon.setCouponNo(3);
        invalidPointCoupon.setName("수정된 쿠폰명");
        invalidPointCoupon.setCouponPoint(0);

        String invalidPointResult = teacherService.setCoupon(invalidPointCoupon);

        Assertions.assertEquals("쿠폰 포인트는 0보다 커야 합니다.", invalidPointResult);


        // NO 3
        // 존재하지 않는 쿠폰 번호
        Coupons notFoundCoupon = new Coupons();
        notFoundCoupon.setCouponNo(99999);
        notFoundCoupon.setName("없는 쿠폰");
        notFoundCoupon.setCouponPoint(300);

        String notFoundResult = teacherService.setCoupon(notFoundCoupon);

        Assertions.assertEquals("수정할 쿠폰을 찾을 수 없습니다.", notFoundResult);


        // YES
        // 존재하는 쿠폰 번호 수정
        int couponNo = 3;

        Coupons coupon = new Coupons();
        coupon.setCouponNo(couponNo);
        coupon.setName("분리수거 면제권");
        coupon.setCouponPoint(2000);

        String result = teacherService.setCoupon(coupon);

        Assertions.assertEquals("쿠폰 정보가 정상적으로 수정되었습니다.", result);

        Coupons updatedCoupon = couponRepository.getCoupon(couponNo);

        Assertions.assertNotNull(updatedCoupon);
        Assertions.assertEquals(couponNo, updatedCoupon.getCouponNo());
        Assertions.assertEquals("분리수거 면제권", updatedCoupon.getName());
        Assertions.assertEquals(2000, updatedCoupon.getCouponPoint());

        log.info("쿠폰 수정 결과 : {}", result);
        log.info("수정된 쿠폰 정보 : {}", updatedCoupon);
    }

    @Test
    public void deleteCouponTest() {
        // NO 1
        // 쿠폰 번호가 0 이하인 경우
        String invalidCouponNoResult = teacherService.deleteCoupon(0);

        Assertions.assertEquals("삭제할 쿠폰 번호가 올바르지 않습니다.", invalidCouponNoResult);


        // NO 2
        // 존재하지 않는 쿠폰 번호
        String notFoundResult = teacherService.deleteCoupon(99999);

        Assertions.assertEquals("삭제할 쿠폰을 찾을 수 없습니다.", notFoundResult);


        // YES
        // 존재하는 쿠폰 번호 삭제
        int couponNo = 4;

        String result = teacherService.deleteCoupon(couponNo);

        Assertions.assertEquals("쿠폰이 정상적으로 삭제되었습니다.", result);
        Assertions.assertNull(couponRepository.getCoupon(couponNo));

        log.info("쿠폰 삭제 결과 : {}", result);
    }

}