package com.school.schoolstock.domain.teacher.repository;

import com.school.schoolstock.domain.stock.vo.Stocks;
import com.school.schoolstock.domain.teacher.dto.response.StudentListResponse;
import com.school.schoolstock.domain.coupon.vo.Coupons;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherRepository {

    // 선생님 계정 존재 여부 확인
    String getTeacherIdCheck(String teacherId);

    // 선생님이 맡은 반 학생 목록 조회
    List<StudentListResponse> getMyStudents(String teacherId);

    // 선생님이 맡은 반 학생 반번호로 아이디 조회
    String getStudentIdInClass(String teacherId, int studentNumber);

    // 선생님 이름 조회(사이드바)
    String getTeacherName(String teacherId);

    // 포인트 지급
    boolean setPointGive(String studentId, int point, String content);

    // 쿠폰 상점에 등록된 쿠폰 정보(쿠폰명, 쿠폰 포인트) 수정
    int setCoupon(Coupons coupon);

    // 쿠폰 상점에 등록된 쿠폰 삭제
    int deleteCoupon(int couponNo);

    // 특정 학생의 보유 쿠폰 '사용'으로 변경
    int updateStudentCouponUsed(String studentId, int couponPurchaseNo);

    // 선생님이 새 주식 등록
    int setStocks(Stocks stock);

    // 선생님 주식 수정
    int updateStock(Stocks stock);

    // 거래 시작된 주식 -> 이름·설명만 수정
    int setUpdateStockInfo(int stockNo, String name, String stockContent);
}