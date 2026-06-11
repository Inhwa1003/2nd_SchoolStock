package com.school.schoolstock.domain.teacher.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

// 선생님이 로그인 후, 처음으로 보여지는 화면입니다. 학생으로 치면, 내 자산 페이지.

public class StudentListResponse {
    private int studentNumber;
    private String name;
    private int totalPoint;
}
