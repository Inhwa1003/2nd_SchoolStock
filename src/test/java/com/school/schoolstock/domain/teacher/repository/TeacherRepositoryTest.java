package com.school.schoolstock.domain.teacher.repository;

import com.school.schoolstock.domain.teacher.dto.response.StudentListResponse;
import com.school.schoolstock.domain.coupon.repository.CouponRepository;
import com.school.schoolstock.domain.coupon.vo.Coupons;
import com.school.schoolstock.domain.teacher.dto.StudentListResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Slf4j
@SpringBootTest
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    void getMyStudentsTest() {
        // given
        String teacherId = "teacher01";

        // when
        List<StudentListResponse> result = teacherRepository.getMyStudents(teacherId);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());

        log.info("조회된 학생 수 = {}", result.size());

        for (StudentListResponse student : result) {
            log.info("학생 정보 = {}", student);
        }
    }

    @Test
    void getTeacherNameTest() {
        // NO
        // 존재하지 않는 선생님 -> null
        Assertions.assertNull(teacherRepository.getTeacherName("nope_teacher"));

        // YES
        // 존재하는 선생님 이름 조회
        String name = teacherRepository.getTeacherName("t0");
        Assertions.assertNotNull(name);
        log.info("선생님 이름 = {}", name);
    }

    @Test
    void getStudentIdInClassTest() {
        String teacherId = "t0";

        // 그 선생 반의 실제 학생 번호 하나 확보
        List<StudentListResponse> students = teacherRepository.getMyStudents(teacherId);
        Assertions.assertFalse(students.isEmpty());
        int existingNumber = students.get(0).getStudentNumber();

        // YES - 내 반 학생 번호 → student_id 반환
        String studentId = teacherRepository.getStudentIdInClass(teacherId, existingNumber);
        Assertions.assertNotNull(studentId);
        log.info("번호 {} -> studentId = {}", existingNumber, studentId);

        // NO
        // 내 반에 없는 번호 -> null
        Assertions.assertNull(teacherRepository.getStudentIdInClass(teacherId, 99999));

        // NO
        // 다른(없는) 선생님으로 조회 -> null
        Assertions.assertNull(teacherRepository.getStudentIdInClass("nope_teacher", existingNumber));

    }

    @Transactional
    @Test
    void setPointGiveTest() {
        // YES
        // 지급 내역(get_point) INSERT 성공 (rows > 0 -> true)
        Assertions.assertTrue(teacherRepository.setPointGive("abc", 5000, "테스트 지급"));

        log.info("get_point INSERT 결과 : " + teacherRepository.setPointGive("abc", 5000, "테스트 지급"));
    }
    void setCouponTest() {
        // 성공 케이스
        int couponNo = 3;

        Coupons coupon = new Coupons();
        coupon.setCouponNo(couponNo);
        coupon.setName("수정된 쿠폰명");
        coupon.setCouponPoint(300);

        int result = teacherRepository.setCoupon(coupon);

        assertThat(result).isEqualTo(1);

        Coupons updatedCoupon = couponRepository.getCoupon(couponNo);

        assertThat(updatedCoupon).isNotNull();
        assertThat(updatedCoupon.getCouponNo()).isEqualTo(couponNo);
        assertThat(updatedCoupon.getName()).isEqualTo("수정된 쿠폰명");
        assertThat(updatedCoupon.getCouponPoint()).isEqualTo(300);


        // 실패 케이스: 존재하지 않는 쿠폰 번호 수정
        int failCouponNo = 99999;

        Coupons failCoupon = new Coupons();
        failCoupon.setCouponNo(failCouponNo);
        failCoupon.setName("없는 쿠폰");
        failCoupon.setCouponPoint(1000);

        int failResult = teacherRepository.setCoupon(failCoupon);

        assertThat(failResult).isEqualTo(0);
        assertThat(couponRepository.getCoupon(failCouponNo)).isNull();
    }

    @Test
    void deleteCouponTest() {
        // NO
        // 존재하지 않는 쿠폰 번호 삭제
        int failCouponNo = 99999;

        int failResult = teacherRepository.deleteCoupon(failCouponNo);

        assertThat(failResult).isEqualTo(0);
        assertThat(couponRepository.getCoupon(failCouponNo)).isNull();


        // YES
        // 존재하는 쿠폰 번호 삭제 => coupon_purchase에서 FK로 참조하기 때문에, coupon_no=4가 되도록, insert 쿼리문 커밋 후 테스트 확인
        int couponNo = 4;

        int result = teacherRepository.deleteCoupon(couponNo);

        assertThat(result).isEqualTo(1);
        assertThat(couponRepository.getCoupon(couponNo)).isNull();
    }
    
}