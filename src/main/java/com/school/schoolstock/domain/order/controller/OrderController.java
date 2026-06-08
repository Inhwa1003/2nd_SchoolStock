package com.school.schoolstock.domain.order.controller;

import com.school.schoolstock.domain.order.dto.request.OrderRequest;
import com.school.schoolstock.domain.order.dto.response.OrderResponse;
import com.school.schoolstock.domain.order.service.OrderService;
import com.school.schoolstock.domain.stock.repository.StockRepository;
import com.school.schoolstock.domain.stock.vo.Stocks;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/schoolstock/s")
public class OrderController {

    private final OrderService orderService;
    private final StockRepository stockRepository;

    // 주식 상세 페이지 화면 반환
    @GetMapping("/orders/{stockNo}")
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
    @GetMapping("/orders/{stockNo}/buy")
    public ResponseEntity<Map<String, Object>> getBuyOrders(@PathVariable int stockNo) {
        try {
            List<OrderResponse> buyOrders = orderService.getStockOrders(stockNo, "BUY");

            if (buyOrders == null || buyOrders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(errorResponse(
                                404,
                                "BUY_ORDER_NOT_FOUND",
                                "대기 중인 매수 주문을 찾을 수 없습니다."
                        ));
            }

            return ResponseEntity.ok(successResponse(
                    200,
                    buyOrders,
                    "대기 매수 주문 목록이 정상적으로 조회되었습니다."
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse(
                            500,
                            "INTERNAL_SERVER_ERROR",
                            "서버 내부 오류로 대기 매수 주문 목록 조회에 실패했습니다."
                    ));
        }
    }

    // 대기 매도 주문 목록 조회
    @ResponseBody
    @GetMapping("/orders/{stockNo}/sell")
    public ResponseEntity<Map<String, Object>> getSellOrders(@PathVariable int stockNo) {
        try {
            List<OrderResponse> sellOrders = orderService.getStockOrders(stockNo, "SELL");

            if (sellOrders == null || sellOrders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(errorResponse(
                                404,
                                "SELL_ORDER_NOT_FOUND",
                                "대기 중인 매도 주문을 찾을 수 없습니다."
                        ));
            }

            return ResponseEntity.ok(successResponse(
                    200,
                    sellOrders,
                    "대기 매도 주문 목록이 정상적으로 조회되었습니다."
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse(
                            500,
                            "INTERNAL_SERVER_ERROR",
                            "서버 내부 오류로 대기 매도 주문 목록 조회에 실패했습니다."
                    ));
        }
    }

// 매도 주문 요청
    @ResponseBody
    @PostMapping("/me/stocks/{stockNo}/sell")
    public ResponseEntity<Map<String, Object>> setSellOrder(
            @PathVariable int stockNo,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody OrderRequest request
    ) {
        try {
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(errorResponse(
                                401,
                                "UNAUTHORIZED",
                                "로그인이 필요합니다."
                        ));
            }

            request.setStockNo(stockNo);

            String studentId = userDetails.getUsername();

            String message = orderService.setSellOrder(studentId, request);

            if ("발행 잔량이 남아 매도요청 할 수 없습니다.".equals(message)
                    || "보유 주식량보다 많은 매도 요청은 할 수 없습니다.".equals(message)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(errorResponse(
                                400,
                                "BAD_REQUEST",
                                message
                        ));
            }

            return ResponseEntity.ok(successResponse(
                    200,
                    null,
                    message
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse(
                            500,
                            "INTERNAL_SERVER_ERROR",
                            "서버 내부 오류로 매도 주문 처리에 실패했습니다."
                    ));
        }
    }

    // 매수 주문 요청
    @ResponseBody
    @PostMapping("/me/stocks/{stockNo}/buy")
    public ResponseEntity<Map<String, Object>> setBuyOrder(
            @PathVariable int stockNo,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody OrderRequest request
    ) {
        try {
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(errorResponse(
                                401,
                                "UNAUTHORIZED",
                                "로그인이 필요합니다."
                        ));
            }

            request.setStockNo(stockNo);

            String studentId = userDetails.getUsername();

            String message = orderService.setBuyOrder(studentId, request);

            if ("보유포인트가 부족합니다.".equals(message)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(errorResponse(
                                400,
                                "BAD_REQUEST",
                                message
                        ));
            }

            return ResponseEntity.ok(successResponse(
                    200,
                    null,
                    message
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse(
                            500,
                            "INTERNAL_SERVER_ERROR",
                            "서버 내부 오류로 매수 주문 처리에 실패했습니다."
                    ));
        }
    }


    // 성공 메세지 보내는 구조: code, data, message => 추후 공통으로 뺄 것
    private Map<String, Object> successResponse(int code, Object data, String message) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("code", code);
        result.put("data", data);
        result.put("message", message);
        return result;
    }

    // 에러 메세지 보내는 구조: status, code, message => 추후 공통으로 뺄 것
    private Map<String, Object> errorResponse(int status, String code, String message) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", status);
        result.put("code", code);
        result.put("message", message);
        return result;
    }
}