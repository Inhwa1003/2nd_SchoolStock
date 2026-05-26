package com.school.schoolstock.domain.stock.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StockVO {
    private int stockNo;
    private String name;
    private String stockContent;
    private int publicationBalance;
    private int publicationPoint;
    private int prevPoint;
}
