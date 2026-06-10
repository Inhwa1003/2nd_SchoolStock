package com.school.schoolstock.domain.teacher.repository;

import com.school.schoolstock.domain.coupon.repository.CouponRepository;
import com.school.schoolstock.domain.coupon.vo.Coupons;
import com.school.schoolstock.domain.teacher.dto.StudentListResponse;
import lombok.extern.slf4j.Slf4j;
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
        assertNotNull(result);
        assertFalse(result.isEmpty());

        log.info("조회된 학생 수 = {}", result.size());

        for (StudentListResponse student : result) {
            log.info("학생 정보 = {}", student);
        }
    }

    @Test
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

    
}