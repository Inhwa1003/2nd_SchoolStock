package com.school.schoolstock.domain.news.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class News {
    private int newsNo;
    private String newsContext;
}
