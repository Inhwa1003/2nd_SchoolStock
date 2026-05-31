package com.school.schoolstock.domain.stock.service;

import com.school.schoolstock.domain.order.repository.OrderRepository;
import com.school.schoolstock.domain.stock.repository.StockRepository;
import com.school.schoolstock.domain.stock.vo.Stocks;
import com.school.schoolstock.domain.student.repository.StudentRepository;
import com.school.schoolstock.domain.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService{
    private final StockRepository stockRepository;
    private final StudentRepository studentRepository;
    private final OrderRepository orderRepository;
    private final TradeRepository tradeRepository;

    @Override
    public String setSellOrder(String studentId, int sellPoint, int sellAmount, int stockNo) {
        Map<String, Object> matchOrder = null;
        //1. 발행 잔량 확인 있으면 학생간 거래x 매도 요청x
        if(stockRepository.getStockPubInfo(stockNo).getPublicationBalance() > 0)
            return "발행 잔량이 남아 매도요청 할 수 없습니다.";
        //2. (보유한 주식 수량 < 매도요청 수량 )체크
        if(studentRepository.getMyStockAmount(studentId, stockNo) < sellAmount)
            return  "보유 주식량보다 많은 매도 요청은 할 수 없습니다.";
        //3. 매수 주문 매칭 시도 (가격 수량 다맞는 조건)
        matchOrder = orderRepository.getMatchOrder(stockNo, sellPoint, sellAmount, studentId,"BUY");

        if(matchOrder != null && !matchOrder.isEmpty()){
            //4. 매칭O
            //4-1. 매수자 주문 '체결'로 업데이트
            orderRepository.setOrderStateMatched(Integer.parseInt(matchOrder.get("orderNo").toString()));
            //4-2. 매도자 주문 '체결'로 insert
            orderRepository.setOrderRequest("SELL", sellPoint, sellAmount, "MATCHED", studentId, stockNo);
            //4-3. 매도요청한 주문 번호로 매도 완료 (매도 요청 주문번호 업무, 매칭 완료 업무 2개)
            tradeRepository.setMatchedOrder(Integer.parseInt(matchOrder.get("orderNo").toString()), orderRepository.getMyOrderNo("SELL", studentId, stockNo, "MATCHED", sellAmount, sellPoint));
            //4-4. 매도자 포인트 증가(매수자는 등록할때 포인트 감소)
            studentRepository.setStudentPointUp(studentId, (sellAmount * sellPoint));
            return "매도가 완료되었습니다.";
        }else{
            //5. 매칭X
            // 5-1. 주문 대기로 요청
            orderRepository.setOrderRequest("SELL", sellPoint, sellAmount, "PENDING", studentId, stockNo);
            return "매도주문이 대기처리 되었습니다.";
        }
    }

    @Override
    public String setBuyOrder(String studentId, int buyPoint, int buyAmount, int stockNo) {
        Map<String, Object> matchOrder = null;
        // 학생이 주문 요청한 가격보다 보유포인트가 적을때 실행
        if(studentRepository.getMyPoint(studentId) < (buyAmount * buyPoint))
            return "보유포인트가 부족합니다.";
        // 1. 발행 개수가 남았는지 체크 있으면 실행
        Stocks pubInfo = stockRepository.getStockPubInfo(stockNo);
        if(pubInfo.getPublicationBalance() > 0){
            // 1-1. 입력한 값이 발행가격과 같거나 높을때 실행
            if(pubInfo.getPublicationPoint() <= buyPoint){
                // 1-2. 발행 개수 차감
                // 발행개수 음수값 차단 작은값으로 거래
                int buyFromPub = Math.min(pubInfo.getPublicationBalance(), buyAmount);
                stockRepository.setStockPubBalance(buyFromPub, stockNo);
                // 1-3. 주문 체결로 바로 요청
                orderRepository.setOrderRequest("BUY", buyPoint, buyFromPub, "MATCHED", studentId, stockNo);
                // 1-4. 매수 요청한 주문번호로 주문 완료 등록
                tradeRepository.setMatchedOrder(orderRepository.getMyOrderNo("BUY", studentId, stockNo, "MATCHED", buyFromPub, buyPoint), null);
                // 1-5. 보유 포인트 차감
                studentRepository.setStudentPointDown(studentId, (buyFromPub * buyPoint));
                return "발행 가격 " + pubInfo.getPublicationPoint() + "P 매수가 완료 되었습니다. 남은 발행잔량은 " + (pubInfo.getPublicationBalance() - buyFromPub) + "주 입니다.";
            }
        }

        matchOrder = orderRepository.getMatchOrder(stockNo, buyPoint, buyAmount, studentId, "SELL");
        // 2. 매수 요청에 따른 매도 요청이 있을경우 실행
        if(matchOrder != null && !matchOrder.isEmpty()){
            // 2-1 매도 주문 '체결'로 업데이트
            orderRepository.setOrderStateMatched(Integer.parseInt(matchOrder.get("orderNo").toString()));
            // 2-2. 주문 체결로 바로 요청
            orderRepository.setOrderRequest("BUY", buyPoint, buyAmount, "MATCHED", studentId, stockNo);
            // 2-3. 매수 요청한 주문번호로 주문 완료 등록
            tradeRepository.setMatchedOrder(orderRepository.getMyOrderNo("BUY", studentId, stockNo, "MATCHED",buyAmount, buyPoint), Integer.parseInt(matchOrder.get("orderNo").toString()));
            // 2-4. 매수 학생 보유 포인트 차감
            studentRepository.setStudentPointDown(studentId, (buyAmount * buyPoint));
            // 2-5. 매도 학생 보유 포인트 증가
            studentRepository.setStudentPointUp(matchOrder.get("studentId").toString(), (buyAmount * buyPoint));
            return "매수가 완료되었습니다.";
        }else {
            // 3. 발행 잔량 다 팔리고 학생간 거래 매칭도 없다면 실행
            // 3-1. 주문 대기로 요청
            orderRepository.setOrderRequest("BUY", buyPoint, buyAmount, "PENDING", studentId, stockNo);
            // 3-2. 매수 학생 보유 포인트 차감
            studentRepository.setStudentPointDown(studentId, (buyAmount * buyPoint));
            return "매수주문이 대기처리 되었습니다.";
        }
    }

    @Transactional(readOnly = true)
    @Override
    public String getStockName(int stockNo) {
        return stockRepository.getStockName(stockNo);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Stocks> getStockNameList() {
        return stockRepository.getStockNameList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Stocks> getStockInfo(int stockNo) {
        return stockRepository.getStockInfo(stockNo);
    }

    @Transactional(readOnly = true)
    @Override
    public int getStockPrice(int stockNo) {
        Stocks stocks = stockRepository.getStockPubInfo(stockNo);
        if(stocks.getPublicationBalance() > 0)
            return stocks.getPublicationPoint();
        return stockRepository.getStockPrice(stockNo);
    }

    @Transactional(readOnly = true)
    @Override
    public int getStockPriceChange(int stockNo) {
        return stockRepository.getStockPriceChange(stockNo);
    }

    @Transactional(readOnly = true)
    @Override
    public double getChangeRate(int stockNo) {
        return stockRepository.getChangeRate(stockNo);
    }

    @Transactional(readOnly = true)
    @Override
    public int getPrevPoint(int stockNo) {
        return stockRepository.getPrevPoint(stockNo);
    }

    @Override
    public boolean setStockPubBalance(int buyAmount, int stockNo) {
        return stockRepository.setStockPubBalance(buyAmount, stockNo);
    }

    @Transactional(readOnly = true)
    @Override
    public Stocks getStockPubInfo(int stockNo) {
        return stockRepository.getStockPubInfo(stockNo);
    }
}
