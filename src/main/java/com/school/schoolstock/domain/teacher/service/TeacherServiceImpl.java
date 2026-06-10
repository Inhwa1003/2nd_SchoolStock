package com.school.schoolstock.domain.teacher.service;

import com.school.schoolstock.domain.coupon.vo.Coupons;
import com.school.schoolstock.domain.teacher.dto.StudentListResponse;
import com.school.schoolstock.domain.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public List<StudentListResponse> getMyStudentsList(String teacherId) {
        String checkedTeacherId = teacherRepository.getTeacherIdCheck(teacherId);

        if (checkedTeacherId == null) {
            return Collections.emptyList();
        }

        return teacherRepository.getMyStudents(checkedTeacherId);
    }

    @Transactional
    @Override
    public String setCoupon(Coupons coupon){
        // 1. 쿠폰명 비어있는지 체크
        if (coupon.getName() == null || coupon.getName().trim().isEmpty()) {
            return "쿠폰명을 입력해주세요.";
        }

        // 2. 쿠폰 포인트가 0 이하인지 체크
        if (coupon.getCouponPoint() <= 0) {
            return "쿠폰 포인트는 0보다 커야 합니다.";
        }


        // 3. 쿠폰 수정된 행이 없으면 실패
        if (teacherRepository.setCoupon(coupon) == 0) {
            return "수정할 쿠폰을 찾을 수 없습니다.";
        }

        // 5. 성공
        return "쿠폰 정보가 정상적으로 수정되었습니다.";
    }
}