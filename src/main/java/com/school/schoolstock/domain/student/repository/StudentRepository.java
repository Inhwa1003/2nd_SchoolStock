package com.school.schoolstock.domain.student.repository;

import com.school.schoolstock.domain.student.dto.response.MyCouponResponse;
import com.school.schoolstock.domain.student.dto.response.MyOrderResponse;
import com.school.schoolstock.domain.student.dto.response.MyPointHistoryResponse;
import com.school.schoolstock.domain.student.dto.response.StudentInfoResponse;
import com.school.schoolstock.domain.student.vo.Students;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentRepository {
    // 아이디 중복 체크
    String getIdCheck(String studentId);

    // 회원가입
    boolean setMember(Students students);

    // 로그인
    Students login(String studentId, String password);

    // 총 자산 조회
    int getMyValue(String studentId);

    // 학생 가용포인트 조회
    int getMyPoint(String studentId);

    // 총 손익 조회
    int getTotalProfit(String studentId);

    // 보유 쿠폰 수량 조회
    int getMyCouponAmount(String studentId);

    // 특정 보유 주식 수량 조회
    int getMyStockAmount(String studentId, int stockNo);

    // 특정 보유 주식 평균가격 조회
    int getAveragePoint(String studentId, int stockNo);

    // 특정 보유 주식 총 구매 비용 조회
    int getPurchasePoint(String studentId, int stockNo);

    // 학생이 구매한 주식 조회
    List<Integer> getMyStockNos(String studentId);

    // 특정 보유 주식 수익금 조회
    int getStockProfit(String studentId, int stockNo);

    // 내 주문 요청 조회
    List<MyOrderResponse> getTotalMyOrder(String studentId, int stockNo);

    // 보유 포인트 증가
    boolean setStudentPointUp(String studentId, int totalPoint);

    // 보유 포인트 차감
    boolean setStudentPointDown(String studentId, int totalPoint);

    // 내 포인트 내역 정보 조회(쿠폰, 지급, 주식)
    List<MyPointHistoryResponse> getMyPointHistoryList(String studentId);

    // 학생 포인트 차감 및 보유쿠폰 수량 증가
    boolean setStudentAssets(String studentId, int point);

    // 보유 쿠폰 정보 조회
    List<MyCouponResponse> getMyCouponList(String studentId);

    // 학생 기본정보 조회 (사이드바 용)
    StudentInfoResponse getStudentInfo(String studentId);
}
