package com.school.schoolstock.domain.student.service;

import com.school.schoolstock.domain.order.dto.response.OrderCancelPointResponse;
import com.school.schoolstock.domain.order.repository.OrderRepository;
import com.school.schoolstock.domain.stock.repository.StockRepository;
import com.school.schoolstock.domain.stock.service.StockService;
import com.school.schoolstock.domain.student.dto.response.*;
import com.school.schoolstock.domain.student.repository.StudentRepository;
import com.school.schoolstock.global.error.BusinessException;
import com.school.schoolstock.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;
    private final StockService stockService;

    @Transactional
    @Override
    // 주식 상세 - 자신의 주문 요청을 취소했을 때
    public void setMyOrderCancel(int orderNo) {
        // 매수
        // EX) 주식 1개 당 1500P를 2개 매수 대기 걸어둠. 3000p 차감된 상태
        // 1. 주문 번호로 환불받아야 할 가격 조회 => 3000P
        OrderCancelPointResponse cancelInfo =
                orderRepository.getCancelOrderPointInfo(orderNo);

        if (cancelInfo == null) {
            throw new BusinessException(ErrorCode.ORDER_NOT_CANCELABLE);
        }
        // 2. 보유 포인트 증가 => 3000P가 증가
        studentRepository.setStudentPointUp(
                cancelInfo.getStudentId(),
                cancelInfo.getRefundPoint()
        );
        
        // 3. 주문 요청 상태가 'PENDING' -> 'CANCELED'로 변경
        orderRepository.setOrderStateCancel(orderNo);


        // 매도 => 지금 할 수 없음. 내 포인트 내역이랑 같이 바꿔야 함.
        // EX) 보유한 주식 2개를 매도 대기 걸어둠. 해당 보유 주식은 총 3개라 가정
        // 1. 주문 번호로, 주문한 주식수량 조회 => 2개 확인
        // 2. 주문한 주식 수량만큼 보유 주식 수량 증가 => 해당 보유 주식은 총 5개로 변경
    }

    @Transactional(readOnly = true)
    @Override
    public MyAssetResponse getMyAsset(String studentId) {
        List<MyStockResponse> myStocks = new ArrayList<>();
        List<Integer> myStockNos = studentRepository.getMyStockNos(studentId);

        for (Integer stockNo : myStockNos) {
            myStocks.add(MyStockResponse.builder()
                    .stockName(stockRepository.getStockName(stockNo))
                    .stockAmount(studentRepository.getMyStockAmount(studentId, stockNo))
                    .nowPoint(stockService.getStockPrice(stockNo))
                    .averagePoint(studentRepository.getAveragePoint(studentId, stockNo))
                    .purchasePoint(studentRepository.getPurchasePoint(studentId, stockNo))
                    .stockProfit(studentRepository.getStockProfit(studentId, stockNo)).build());
        }
        return MyAssetResponse.builder()
                .totalValue(studentRepository.getMyValue(studentId))
                .myPoint(studentRepository.getMyPoint(studentId))
                .totalProfit(studentRepository.getTotalProfit(studentId))
                .couponAmount(studentRepository.getMyCouponAmount(studentId))
                .myStocks(myStocks).build();
    }

    @Transactional
    @Override
    // 선생님
    public boolean setStudentPointUp(String studentId, int totalPoint) {
        return studentRepository.setStudentPointUp(studentId, totalPoint);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MyPointHistoryResponse> getMyPointHistory(String studentId) {
        return studentRepository.getMyPointHistoryList(studentId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MyOrderResponse> getMyOrder(String studentId, int stockNo) {
        return studentRepository.getTotalMyOrder(studentId, stockNo);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MyCouponResponse> getMyCoupon(String studentId) {
        return studentRepository.getMyCouponList(studentId);
    }

    @Transactional(readOnly = true)
    @Override
    public StudentInfoResponse getStudentInfo(String studentId) {
        return studentRepository.getStudentInfo(studentId);
    }
}
