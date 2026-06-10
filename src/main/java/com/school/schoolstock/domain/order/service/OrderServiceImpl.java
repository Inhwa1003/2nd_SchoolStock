package com.school.schoolstock.domain.order.service;


import com.school.schoolstock.domain.order.dto.request.BuySellOrderRequest;
import com.school.schoolstock.domain.order.dto.response.StockOrderResponse;
import com.school.schoolstock.domain.order.repository.OrderRepository;
import com.school.schoolstock.domain.order.vo.Orders;
import com.school.schoolstock.domain.stock.repository.StockRepository;
import com.school.schoolstock.domain.stock.vo.Stocks;
import com.school.schoolstock.domain.student.repository.StudentRepository;
import com.school.schoolstock.domain.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// 매도 기능: setSellOrder
// 매수 기능: setBuyOrder
// 매수, 매도 조회 기능: getStockOrders

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;
    private final StudentRepository studentRepository;
    private final TradeRepository tradeRepository;

    // 매수, 매도 주문 토글로 조회하는 기능
    @Transactional(readOnly = true)
    @Override
    public List<StockOrderResponse> getStockOrders(int stockNo, String content) {
        List<Orders> orders = content.equals("BUY")? orderRepository.getTotalBuyOrder(stockNo): orderRepository.getTotalSellOrder(stockNo);
        List<StockOrderResponse> result = new ArrayList<>();

        for (Orders order : orders) {
            result.add(StockOrderResponse.builder()
                    .orderContent(order.getOrderContent())
                    .orderPoint(order.getOrderPoint())
                    .orderAmount(order.getAmount()).build());
        }

        return result;
    }

    // 매도 기능
    @Transactional
    @Override
    public String setSellOrder(String studentId, BuySellOrderRequest request) {
        Map<String, Object> matchOrder;
        //1. 발행 잔량 확인 있으면 학생간 거래x 매도 요청x
        if(stockRepository.getStockPubInfo(request.getStockNo()).getPublicationBalance() > 0)
            return "발행 잔량이 남아 매도요청 할 수 없습니다.";

        //2. (보유한 주식 수량 < 매도요청 수량 )체크
        if(studentRepository.getMyStockAmount(studentId, request.getStockNo()) < request.getOrderAmount())
            return  "보유 주식량보다 많은 매도 요청은 할 수 없습니다.";

        //3. 매수 주문 매칭 시도 (가격 수량 다맞는 조건)
        matchOrder = orderRepository.getMatchOrder(request.getStockNo(), request.getOrderPoint(), request.getOrderAmount(), studentId,"BUY");

        if(matchOrder != null && !matchOrder.isEmpty()){
            //4. 매칭O
            //4-1. 매수자 주문 '체결'로 업데이트
            orderRepository.setOrderStateMatched(Integer.parseInt(matchOrder.get("orderNo").toString()));
            //4-2. 매도자 주문 '체결'로 insert
            orderRepository.setOrderRequest("SELL", request.getOrderPoint(), request.getOrderAmount(), "MATCHED", studentId, request.getStockNo());
            //4-3. 매도요청한 주문 번호로 매도 완료 (매도 요청 주문번호 업무, 매칭 완료 업무 2개)
            tradeRepository.setMatchedOrder(Integer.parseInt(matchOrder.get("orderNo").toString()), orderRepository.getMyOrderNo("SELL", studentId, request.getStockNo(), "MATCHED", request.getOrderAmount(), request.getOrderPoint()));
            //4-4. 매도자 포인트 증가(매수자는 등록할때 포인트 감소)
            studentRepository.setStudentPointUp(studentId, (request.getOrderAmount() * request.getOrderPoint()));
            return "매도가 완료되었습니다.";

        }else{

            //5. 매칭X
            // 5-1. 주문 대기로 요청
            orderRepository.setOrderRequest("SELL", request.getOrderPoint(), request.getOrderAmount(), "PENDING", studentId, request.getStockNo());
            return "매도주문이 대기처리 되었습니다.";
        }
    }

    // 매수 기능
    @Transactional
    @Override
    public String setBuyOrder(String studentId, BuySellOrderRequest request) {
        Map<String, Object> matchOrder;
        // 학생이 주문 요청한 가격보다 보유포인트가 적을때 실행
        if(studentRepository.getMyPoint(studentId) < (request.getOrderAmount() * request.getOrderPoint()))
            return "보유포인트가 부족합니다.";

        Stocks pubInfo = stockRepository.getStockPubInfo(request.getStockNo());

    // 현재가격보다 낮은 매수 요청 차단
        if (request.getOrderPoint() < pubInfo.getPublicationPoint()) {
            return "현재가격 이상으로만 매수 주문이 가능합니다.";
        }

    // 1. 발행 개수가 남았는지 체크 있으면 실행
        if (pubInfo.getPublicationBalance() > 0) {

            // 발행개수 음수값 차단 작은값으로 거래
            int buyFromPub = Math.min(pubInfo.getPublicationBalance(), request.getOrderAmount());

            // 1-2. 발행 개수 차감
            stockRepository.setStockPubBalance(buyFromPub, request.getStockNo());

            // 1-3. 주문 체결로 바로 요청
            orderRepository.setOrderRequest(
                    "BUY",
                    pubInfo.getPublicationPoint(),
                    buyFromPub,
                    "MATCHED",
                    studentId,
                    request.getStockNo()
            );

            // 1-4. 매수 요청한 주문번호로 주문 완료 등록
            tradeRepository.setMatchedOrder(
                    orderRepository.getMyOrderNo(
                            "BUY",
                            studentId,
                            request.getStockNo(),
                            "MATCHED",
                            buyFromPub,
                            pubInfo.getPublicationPoint()
                    ),
                    null
            );

            // 1-5. 보유 포인트 차감
            studentRepository.setStudentPointDown(
                    studentId,
                    buyFromPub * pubInfo.getPublicationPoint()
            );

            return "발행 가격 " + pubInfo.getPublicationPoint()
                    + "P 매수가 완료 되었습니다. 남은 발행잔량은 "
                    + (pubInfo.getPublicationBalance() - buyFromPub)
                    + "주 입니다.";
        }

        matchOrder = orderRepository.getMatchOrder(
                request.getStockNo(),
                request.getOrderPoint(),
                request.getOrderAmount(),
                studentId,
                "SELL"
        );

        // 2. 매수 요청에 따른 매도 요청이 있을경우 실행
        if(matchOrder != null && !matchOrder.isEmpty()){
            // 2-1 매도 주문 '체결'로 업데이트
            orderRepository.setOrderStateMatched(Integer.parseInt(matchOrder.get("orderNo").toString()));
            // 2-2. 주문 체결로 바로 요청
            orderRepository.setOrderRequest("BUY", request.getOrderPoint(), request.getOrderAmount(), "MATCHED", studentId, request.getStockNo());
            // 2-3. 매수 요청한 주문번호로 주문 완료 등록
            tradeRepository.setMatchedOrder(orderRepository.getMyOrderNo("BUY", studentId, request.getStockNo(), "MATCHED", request.getOrderAmount(), request.getOrderPoint()), Integer.parseInt(matchOrder.get("orderNo").toString()));
            // 2-4. 매수 학생 보유 포인트 차감
            studentRepository.setStudentPointDown(studentId, (request.getOrderAmount() * request.getOrderPoint()));
            // 2-5. 매도 학생 보유 포인트 증가
            studentRepository.setStudentPointUp(matchOrder.get("studentId").toString(), (request.getOrderAmount() * request.getOrderPoint()));
            return "매수가 완료되었습니다.";

        }else {

            // 3. 발행 잔량 다 팔리고 학생간 거래 매칭도 없다면 실행
            // 3-1. 주문 대기로 요청
            orderRepository.setOrderRequest("BUY", request.getOrderPoint(), request.getOrderAmount(), "PENDING", studentId, request.getStockNo());
            // 3-2. 매수 학생 보유 포인트 차감
            studentRepository.setStudentPointDown(studentId, (request.getOrderAmount() * request.getOrderPoint()));
            return "매수주문이 대기처리 되었습니다.";
        }
    }
}
