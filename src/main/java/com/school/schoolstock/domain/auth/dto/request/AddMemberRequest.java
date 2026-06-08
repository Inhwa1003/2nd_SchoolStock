package com.school.schoolstock.domain.auth.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "password")
@Builder
public class AddMemberRequest {
    private String loginId;
    private String email;
    private String password;
    private String name;
    private int grade;
    private String className;
    private int studentNumber;
}
