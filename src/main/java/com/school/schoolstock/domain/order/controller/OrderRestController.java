package com.school.schoolstock.domain.order.controller;

import com.school.schoolstock.domain.order.dto.request.BuySellOrderRequest;
import com.school.schoolstock.domain.order.service.OrderService;
import com.school.schoolstock.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schoolstock/s")
public class OrderRestController {
    private final OrderService orderService;

    // 대기 매수 주문 목록 조회
    @GetMapping("/orders/{stockNo}/buy")
    public ApiResponse getBuyOrders(@PathVariable int stockNo) {
        return ApiResponse.of(200, "대기 매수 주문 목록이 정상적으로 조회되었습니다.", orderService.getStockOrders(stockNo, "BUY"));
    }

    // 대기 매도 주문 목록 조회
    @GetMapping("/orders/{stockNo}/sell")
    public ApiResponse getSellOrders(@PathVariable int stockNo) {
        return ApiResponse.of(200,"대기 매도 주문 목록이 정상적으로 조회되었습니다.", orderService.getStockOrders(stockNo, "SELL"));
    }

    // 매도 주문 요청
    @PostMapping("/me/stocks/{stockNo}/sell")
    public ApiResponse setSellOrder(
            @PathVariable int stockNo,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BuySellOrderRequest request) {
        request.setStockNo(stockNo);
        return ApiResponse.of(200, orderService.setSellOrder(userDetails.getUsername(), request), null);
    }

    // 매수 주문 요청
    @PostMapping("/me/stocks/{stockNo}/buy")
    public ApiResponse setBuyOrder(
            @PathVariable int stockNo,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BuySellOrderRequest request) {
        request.setStockNo(stockNo);
        return ApiResponse.of(200, orderService.setBuyOrder(userDetails.getUsername(), request), null);
    }
}
