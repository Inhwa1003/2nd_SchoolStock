package com.school.schoolstock.domain.stock.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class StockUpdateRequest {
    private int stockNo;
    private String name;
    private String stockContent;
    private int publicationBalance;
    private int publicationPoint;
}
