package com.school.schoolstock.domain.trade.vo;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

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
