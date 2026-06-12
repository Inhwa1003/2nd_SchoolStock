package com.school.schoolstock.domain.teacher.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PointGrantRequest {
    private int points;
    private String content;
}
