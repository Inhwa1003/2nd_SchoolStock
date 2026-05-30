package com.school.schoolstock.domain.order.service;

import com.school.schoolstock.domain.order.repository.OrderRepository;
import com.school.schoolstock.domain.order.vo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    // 주문 매칭
    public Map<String, Object> getMatchOrder(int stockNo, int orderPrice, int orderAmount, String studentId, String content){
        return orderRepository.getMatchOrder(
                stockNo,
                orderPrice,
                orderAmount,
                studentId,
                content
        );
    }

    // 매도, 매수 주문 요청
    public boolean setOrderRequest(String content, int price, int amount, String state, String studentId, int stockNo){
        return orderRepository.setOrderRequest(
                content,
                price,
                amount,
                state,
                studentId,
                stockNo
        );
    }

    // 최근 등록한 주문 번호 조회
    public int getMyOrderNo(String content, String studentId, int stockNo, String state, int amount, int price){
        return orderRepository.getMyOrderNo(
                content,
                studentId,
                stockNo,
                state,
                amount,
                price
        );
    }

    // 특정 주식 대기중인 매도 매수 주문 모두 조회
    public List<Orders> getTotalOrder(int stockNo){
        return orderRepository.getTotalOrder(
                stockNo
        );
    }

    // 특정 주식 대기중인 매도 주문 모두 조회
    public List<Orders> getTotalSellOrder(int stockNo){
        return orderRepository.getTotalSellOrder(
                stockNo
        );
    }

    // 특정 주식 대기중인 매수 주문 모두 조회
    public List<Orders> getTotalBuyOrder(int stockNo){
        return orderRepository.getTotalBuyOrder(
                stockNo
        );
    }

    // 주문 요청 상태 '대기'변경
    public boolean setOrderStatePending(int orderNo){
        return orderRepository.setOrderStatePending(
                orderNo
        );
    }

    // 주문 요청 상태 '취소'변경
    public boolean setOrderStateCancel(int orderNo){
        return orderRepository.setOrderStateCancel(
                orderNo
        );
    }

    // 주문 요청 상태 '체결' 변경
    public boolean setOrderStateMatched(int orderNo){
        return orderRepository.setOrderStateMatched(
                orderNo
        );
    }

}
