package com.school.schoolstock.domain.news.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse {

    private int newsNo;
    private String newsContent;

}
