package com.school.schoolstock.domain.stock.controller;

import com.school.schoolstock.domain.stock.dto.response.StockListPageResponse;
import com.school.schoolstock.domain.stock.dto.response.StockPriceResponse;
import com.school.schoolstock.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schoolstock/s/stocks")
public class StockRestController {

    private final StockService stockService;

    @GetMapping("/prices")
    public List<StockListPageResponse> getPrices(){
        return stockService.getStockList();
    }

    @GetMapping("/{stockNo}/price")
    public StockPriceResponse getPrice(@PathVariable int stockNo){
        return stockService.getStockPriceInfo(stockNo);
    }

}
