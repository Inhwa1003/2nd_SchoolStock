package com.school.schoolstock.domain.student.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class StudentInfoResponse {
    private String name;
    private int grade;
    private String className;
    private int studentNumber;
    private int totalPoint;
}
