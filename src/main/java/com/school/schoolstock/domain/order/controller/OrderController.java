package com.school.schoolstock.domain.order.controller;

import com.school.schoolstock.domain.order.dto.response.OrderResponse;
import com.school.schoolstock.domain.order.service.OrderService;
import com.school.schoolstock.domain.stock.repository.StockRepository;
import com.school.schoolstock.domain.stock.vo.Stocks;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/schoolstock/s/orders")
public class OrderController {

    private final OrderService orderService;
    private final StockRepository stockRepository;

    // 주식 상세 페이지 화면 반환
    @GetMapping("/{stockNo}")
    public String getStockDetailPage(@PathVariable int stockNo, Model model) {

        Stocks stock = stockRepository.getStockPubInfo(stockNo);

        model.addAttribute("stockNo", stock.getStockNo());
        model.addAttribute("stockName", stock.getName());
        model.addAttribute("stockContent", stock.getStockContent());

        // 현재 Stocks VO에는 nowPrice가 없어서 publicationPoint를 현재가처럼 사용
        model.addAttribute("nowPrice", stock.getPublicationPoint());

        model.addAttribute("prevPrice", stock.getPrevPoint());

        return "stockDetail";
    }

    // 대기 매수 주문 목록 조회
    @ResponseBody
    @GetMapping("/{stockNo}/buy")
    public ResponseEntity<Map<String, Object>> getBuyOrders(@PathVariable int stockNo) {
        try {
            List<OrderResponse> buyOrders = orderService.getStockOrders(stockNo, "BUY");

            if (buyOrders == null || buyOrders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(response(
                                404,
                                null,
                                "대기 중인 매수 주문을 찾을 수 없습니다."
                        ));
            }

            return ResponseEntity.ok(response(
                    200,
                    buyOrders,
                    "대기 매수 주문 목록이 정상적으로 조회되었습니다."
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response(
                            500,
                            null,
                            "서버 내부 오류로 대기 매수 주문 목록 조회에 실패했습니다."
                    ));
        }
    }

    // 대기 매도 주문 목록 조회
    @ResponseBody
    @GetMapping("/{stockNo}/sell")
    public ResponseEntity<Map<String, Object>> getSellOrders(@PathVariable int stockNo) {
        try {
            List<OrderResponse> sellOrders = orderService.getStockOrders(stockNo, "SELL");

            if (sellOrders == null || sellOrders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(response(
                                404,
                                null,
                                "대기 중인 매도 주문을 찾을 수 없습니다."
                        ));
            }

            return ResponseEntity.ok(response(
                    200,
                    sellOrders,
                    "대기 매도 주문 목록이 정상적으로 조회되었습니다."
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response(
                            500,
                            null,
                            "서버 내부 오류로 대기 매도 주문 목록 조회에 실패했습니다."
                    ));
        }
    }

    private Map<String, Object> response(int code, Object data, String message) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("code", code);
        response.put("data", data);
        response.put("message", message);
        return response;
    }
}