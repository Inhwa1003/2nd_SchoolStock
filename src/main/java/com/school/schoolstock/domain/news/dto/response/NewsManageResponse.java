package com.school.schoolstock.domain.news.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class NewsManageResponse {
    private int newsNo;
    private String newsContent;
}
