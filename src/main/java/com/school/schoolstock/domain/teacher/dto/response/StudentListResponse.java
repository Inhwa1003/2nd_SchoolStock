package com.school.schoolstock.domain.teacher.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class StudentListResponse {
    private int studentNumber;
    private String name;
    private int totalPoint;
}
