package com.school.schoolstock.global.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse {
    private final int code;
    private final String message;
    private final Object data;
    public static ApiResponse of(int code, String message, Object data) {
        return ApiResponse.builder()
                .code(code)
                .message(message)
                .data(data).build();
    }
}
