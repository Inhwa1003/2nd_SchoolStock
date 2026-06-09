package com.school.schoolstock.domain.stock.service;

import com.school.schoolstock.domain.stock.dto.response.StockDetailResponse;
import com.school.schoolstock.domain.stock.dto.response.StockListResponse;
import com.school.schoolstock.domain.stock.dto.response.StockPriceResponse;
import com.school.schoolstock.domain.stock.repository.StockRepository;
import com.school.schoolstock.domain.stock.vo.Stocks;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;

    // 주식 현재가격(포인트) 가져오기 -> 발행잔량이 존재할 때, 발행 포인트 표시
    @Transactional(readOnly = true)
    @Override
    public int getStockPrice(int stockNo) {
        Stocks stock = stockRepository.getStockPubInfo(stockNo);
        if (stock.getPublicationBalance() > 0)
            return stock.getPublicationPoint();
        return stockRepository.getStockPrice(stockNo);
    }

    // 현재 포인트
    @Transactional(readOnly = true)
    @Override
    public StockPriceResponse getStockPriceInfo(int stockNo) {
        int now = getStockPrice(stockNo);
        int prev = stockRepository.getPrevPoint(stockNo);

        int priceChange = now - prev;
        double changeRate = (prev == 0) ? 0.0 : (now - prev) * 100.0 / prev;

        return StockPriceResponse.builder()
                .nowPoint(now)
                .priceChange(priceChange)
                .changeRate(changeRate).build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<StockListResponse> getStockList() {
        List<StockListResponse> result = new ArrayList<>();

        for (Stocks stock : stockRepository.getStockNameList()) {

            StockPriceResponse price = getStockPriceInfo(stock.getStockNo());

            result.add(StockListResponse.builder()
                    .stockNo(stock.getStockNo())
                    .name(stock.getName())
                    .nowPoint(price.getNowPoint())
                    .priceChange(price.getPriceChange())
                    .changeRate(price.getChangeRate()).build());
        }
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public StockDetailResponse getStockDetail(int stockNo) {
        Stocks info = stockRepository.getStockInfo(stockNo);
        Stocks pub =  stockRepository.getStockPubInfo(stockNo);
        StockPriceResponse price = getStockPriceInfo(stockNo);

        return StockDetailResponse.builder()
                .stockNo(stockNo)
                .name(info.getName())
                .stockContent(info.getStockContent())
                .publicationBalance(pub.getPublicationBalance())
                .publicationPoint(pub.getPublicationPoint())
                .nowPoint(price.getNowPoint())
                .prevPoint(stockRepository.getPrevPoint(stockNo))
                .priceChange(price.getPriceChange())
                .changeRate(price.getChangeRate()).build();
    }

}
