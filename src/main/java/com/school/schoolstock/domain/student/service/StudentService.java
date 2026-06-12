package com.school.schoolstock.domain.student.service;

import com.school.schoolstock.domain.student.dto.response.*;

import java.util.List;


public interface StudentService {
    //내 요청 주문 취소
    void setMyOrderCancel(int orderNo);

    //내자산 페이지 화면 값 가져오는 서비스 비동기포함 두개로 표시해둔거 Controller에서 분기 처리로 2개 구현 하면 될듯
    MyAssetResponse getMyAsset(String studentId);

    //보유 포인트 증가 (지급으로 보면 선생님 추가될때 그쪽 Service로 이동되면 좋을것같음)
    boolean setStudentPointUp(String studentId, int totalPoint);

    //내 포인트 내역 정보 조회(쿠폰, 지급, 주식) UI로딩
    List<MyPointHistoryResponse> getMyPointHistory(String studentId);

    //내 주문 요청 조회
    List<MyOrderResponse> getMyOrder(String studentId, int stockNo);

    //보유 쿠폰 정보 조회
    List<MyCouponResponse> getMyCouponList(String studentId);

    //학생 기본정보 조회 (사이드바 용)
    StudentInfoResponse getStudentInfo(String studentId);
}
