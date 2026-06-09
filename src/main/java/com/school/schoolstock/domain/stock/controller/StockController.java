package com.school.schoolstock.domain.stock.controller;

import com.school.schoolstock.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/schoolstock/s/stocks")
public class StockController {

    private final StockService stockService;

    @GetMapping
    public String getStockList(Model model) {

        model.addAttribute("stockList", stockService.getStockList());
        return "stockList";
    }


}
