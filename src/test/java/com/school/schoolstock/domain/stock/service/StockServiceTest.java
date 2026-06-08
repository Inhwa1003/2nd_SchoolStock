package com.school.schoolstock.domain.stock.service;

import com.school.schoolstock.domain.stock.dto.response.StockDetailResponse;
import com.school.schoolstock.domain.stock.dto.response.StockListResponse;
import com.school.schoolstock.domain.stock.dto.response.StockPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@SpringBootTest
public class StockServiceTest {
    @Autowired
    private StockServiceImpl stockService;
    @Test
    void getStockPrice(){
        //NO
        //존재하지 않는 주식번호(null반환을 stocks에서 받아 null로 값을 조회 해서 NullPointerException예외발생)
        Assertions.assertThrows(NullPointerException.class, () -> stockService.getStockPrice(111));
        //YES
        log.info("주식 현재 가격 : " + stockService.getStockPrice(1));
    }
    @Test
    void getStockPriceInfoTest() {
        // YES
        StockPriceResponse p = stockService.getStockPriceInfo(1);
        Assertions.assertNotNull(p);
        log.info("현재가:{}, (현재가-이전가):{}, 등락률:{}", p.getNowPoint(), p.getPriceChange(), p.getChangeRate());
    }

    @Test
    void getStockDetailTest() {
        // YES
        StockDetailResponse d = stockService.getStockDetail(1);
        Assertions.assertNotNull(d);
        log.info("상세 -> 이름:{}, 발행잔량:{}, 발행가:{}, 현재가:{}, 이전가:{}, 등락률:{}",
                d.getName(), d.getPublicationBalance(), d.getPublicationPoint(),
                d.getNowPoint(), d.getPrevPoint(), d.getChangeRate());
        // NO — 없는 종목 -> getStockInfo null -> info.getName()에서 NPE
        Assertions.assertThrows(NullPointerException.class,
                () -> stockService.getStockDetail(9999));
    }

    @Test
    void getStockListTest() {
        // YES
        List<StockListResponse> list = stockService.getStockList();
        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
        log.info("주식목록: {}", list);
    }

}
