package com.school.schoolstock.domain.teacher.service;

import com.school.schoolstock.domain.student.repository.StudentRepository;
import com.school.schoolstock.domain.teacher.dto.response.StudentListResponse;
import com.school.schoolstock.domain.coupon.vo.Coupons;
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
        return teacherRepository.getStudentIdInClass(teacherId, studentNumber);
    }

    @Override
    public String getTeacherName(String teacherId) {
        return teacherRepository.getTeacherName(teacherId);
    }

    @Transactional
    @Override
    public boolean givePoint(String studentId, int point, String content) {
        // 예외 처리 필요함 지급 안될경우
        studentRepository.setStudentPointUp(studentId, point);
        teacherRepository.setPointGive(studentId, point, content);
        return true;
    }
    // 쿠폰 상점의 쿠폰 정보(쿠폰명, 쿠폰 포인트) 수정
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

    // 쿠폰 상점에 있는 쿠폰을 삭제
    @Transactional
    @Override
    public String deleteCoupon(int couponNo) {

        // 쿠폰 번호 유효성 확인
        if (couponNo <= 0) {
            return "삭제할 쿠폰 번호가 올바르지 않습니다.";
        }

        // 쿠폰 삭제
        int result = teacherRepository.deleteCoupon(couponNo);

        // 삭제된 행이 없으면 쿠폰 번호가 없는 것
        if (result == 0) {
            return "삭제할 쿠폰을 찾을 수 없습니다.";
        }

        return "쿠폰이 정상적으로 삭제되었습니다.";
    }

    // 특정 학생의 보유 쿠폰 상태 '사용'으로 수정
    @Transactional
    @Override
    public CouponUseResult useStudentCoupon(String teacherId, int studentNumber, int couponPurchaseNo) {

        // 1. 선생님이 맡은 반 학생인지 확인
        String studentId = teacherRepository.getStudentIdInClass(teacherId, studentNumber);

        if (studentId == null) {
            return CouponUseResult.STUDENT_NOT_IN_CLASS;
        }

        // 2. 해당 학생의 보유 쿠폰 사용 처리
        int result = teacherRepository.setStudentCouponUsed(studentId, couponPurchaseNo);

        // 3. 수정된 행이 없으면 실패
        if (result == 0) {
            return CouponUseResult.COUPON_USE_FAILED;
        }

        // 4. 성공
        return CouponUseResult.SUCCESS;
    }

}