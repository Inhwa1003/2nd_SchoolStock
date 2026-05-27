package com.school.schoolstock.domain.order.repository;

import com.school.schoolstock.domain.order.vo.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderRepository {
    // 매도 주문 매칭
    public Map<String, Object> getMatchOrder(int stockNo, int orderPrice, int orderAmount, String studentId, String content);

    // 주문 요청 상태 '체결' 변경
    public boolean setOrderStateMatched(int orderNo);

    // 매도, 매수 주문 요청
    public boolean setOrderRequest(String content, int price, int amount, String state, String studentId, int stockNo);

    // 최근 등록한 주문 번호 조회
    public int getMyOrderNo(String content, String studentId, int stockNo, String state, int amount, int price);

    // 특정 주식 대기중인 매도 매수 주문 모두 조회
    public List<Orders> getTotalOrder(int stockNo);

    // 특정 주식 대기중인 매도 주문 모두 조회
    public List<Orders> getTotalSellOrder(int stockNo);

    // 특정 주식 대기중인 매수 주문 모두 조회
    public List<Orders> getTotalBuyOrder(int stockNo);

    // 주문 요청 상태 '대기'변경
    public boolean setOrderStatePending(int orderNo);

    // 주문 요청 상태 '취소'변경
    public boolean setOrderStateCancel(int orderNo);


}