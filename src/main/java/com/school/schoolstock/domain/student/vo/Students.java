package com.school.schoolstock.domain.student.vo;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"password"})
public class Students {
    private String studentId;
    private String password;
    private String name;
    private int grade;
    private String className;
    private int studentNumber;
    private LocalDate registerYear;
    private int totalCoupon;
    private int totalPoint;
}