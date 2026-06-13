package com.school.schoolstock.domain.teacher.dto.request;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class StockCreateRequest {
    private String name;
    private String stockContent;
    private int publicationBalance;
    private int publicationPoint;
}
