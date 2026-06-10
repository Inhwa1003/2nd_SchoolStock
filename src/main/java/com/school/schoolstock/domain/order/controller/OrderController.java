package com.school.schoolstock.domain.order.controller;

import com.school.schoolstock.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
@RequestMapping("/schoolstock/s")
public class OrderController {

    private final StockService stockService;

    // 주식 상세 페이지 화면 반환
    @GetMapping("/orders/{stockNo}")
    public String getStockDetailPage(@PathVariable int stockNo, Model model) {

        model.addAttribute("stockDetail", stockService.getStockDetail(stockNo));

        return "stockDetail";
    }
}