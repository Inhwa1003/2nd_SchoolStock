package com.school.schoolstock.domain.student.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class MyPointHistoryResponse {
    private LocalDateTime historyDate;
    private String historyType;
    private String historyContent;
    private int pointChange;
}
