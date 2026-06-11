package com.school.schoolstock.domain.teacher.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PointGrantRequest {
    private int points;
    private String content;
}
