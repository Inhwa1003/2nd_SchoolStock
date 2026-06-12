package com.school.schoolstock.domain.teacher.service;

import com.school.schoolstock.domain.coupon.dto.CouponUpdateRequest;
import com.school.schoolstock.domain.student.repository.StudentRepository;
import com.school.schoolstock.domain.teacher.dto.request.PointGrantRequest;
import com.school.schoolstock.domain.teacher.dto.response.StudentListResponse;
import com.school.schoolstock.domain.coupon.vo.Coupons;
import com.school.schoolstock.domain.teacher.repository.TeacherRepository;
import com.school.schoolstock.global.error.BusinessException;
import com.school.schoolstock.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    // 학년과 반이 같은 학생들의 정보를 불러오기 => 담임 선생님이 맡은 반의 학생들의 정보를 조회
    @Override
    public List<StudentListResponse> getMyStudentsList(String teacherId) {
        String checkedTeacherId = teacherRepository.getTeacherIdCheck(teacherId);

        if (checkedTeacherId == null) {
            return Collections.emptyList();
        }

        return teacherRepository.getMyStudents(checkedTeacherId);
    }

    @Override
    public String getStudentIdInClass(String teacherId, int studentNumber) {
        String studentId = teacherRepository.getStudentIdInClass(teacherId, studentNumber);
        if(studentId == null)
            throw new BusinessException(ErrorCode.STUDENT_NOT_FOUND);
        return studentId;
    }

    @Override
    public String getTeacherName(String teacherId) {
        return teacherRepository.getTeacherName(teacherId);
    }

    @Transactional
    @Override
    public void givePoint(String teacherId, int studentNumber, PointGrantRequest request) {
        String studentId = getStudentIdInClass(teacherId, studentNumber);
        studentRepository.setStudentPointUp(studentId, request.getPoints());
        teacherRepository.setPointGive(studentId, request.getPoints(), request.getContent());
    }
    // 쿠폰 상점의 쿠폰 정보(쿠폰명, 쿠폰 포인트) 수정
    @Transactional
    @Override
    public void setCoupon(CouponUpdateRequest request){
        // 1. 쿠폰명 비어있는지 체크
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }

        // 2. 쿠폰 포인트가 0 이하인지 체크
        if (request.getCouponPoint() <= 0) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }

        // 3. 쿠폰 수정된 행이 없으면 실패
        if (teacherRepository.setCoupon(Coupons.builder()
                .couponNo(request.getCouponNo())
                .name(request.getName())
                .couponPoint(request.getCouponPoint()).build()) == 0) {
            throw new BusinessException(ErrorCode.COUPON_NOT_FOUND);
        }
    }

    // 쿠폰 상점에 있는 쿠폰을 삭제
    @Transactional
    @Override
    public void deleteCoupon(int couponNo) {

        // 쿠폰 번호 유효성 확인
        if (couponNo <= 0)
            throw new BusinessException(ErrorCode.INVALID_INPUT);

        // 삭제된 행이 없으면 쿠폰 번호가 없는 것
        if(teacherRepository.deleteCoupon(couponNo) == 0);
            throw new BusinessException(ErrorCode.COUPON_NOT_FOUND);
    }

}