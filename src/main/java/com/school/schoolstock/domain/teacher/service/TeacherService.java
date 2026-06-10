package com.school.schoolstock.domain.teacher.service;

import com.school.schoolstock.domain.coupon.vo.Coupons;
import com.school.schoolstock.domain.teacher.dto.StudentListResponse;

import java.util.List;

public interface TeacherService {

    // 선생님이 맡은 반 학생 목록 조회
    List<StudentListResponse> getMyStudentsList(String teacherId);

    // 쿠폰 상점 내 쿠폰명, 쿠폰 포인트 수정
    String setCoupon(Coupons coupon);

}