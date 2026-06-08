package com.school.schoolstock.domain.student.service;

import com.school.schoolstock.domain.order.repository.OrderRepository;
import com.school.schoolstock.domain.stock.repository.StockRepository;
import com.school.schoolstock.domain.stock.service.StockService;
import com.school.schoolstock.domain.student.dto.response.*;
import com.school.schoolstock.domain.student.repository.StudentRepository;
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
    public boolean setMyOrderCancel(int orderNo) {
        return orderRepository.setOrderStateCancel(orderNo);
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
}
