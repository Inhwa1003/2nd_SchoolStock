package com.school.schoolstock.domain.trade.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Trade {

    private int tradeNo;
    private int sellOrderNo;
    private int buyOrderNo;
    private LocalDateTime tradeDate;

}
