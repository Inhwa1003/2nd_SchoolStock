package com.school.schoolstock.domain.news.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NewsUpdateRequest {
    private int newsNo;
    private String newsContent;
}
