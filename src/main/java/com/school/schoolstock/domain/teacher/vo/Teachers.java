package com.school.schoolstock.domain.teacher.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"password"})
public class Teachers {
    private String teacherId;
    // password는 users에 있음. student와 동일하게 기능 처리
    private String name;
    private int grade;
    private String className;
}
