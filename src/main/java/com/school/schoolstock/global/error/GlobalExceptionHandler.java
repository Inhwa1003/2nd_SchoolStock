package com.school.schoolstock.global.error;

import com.school.schoolstock.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBussiness(BusinessException e) {
        ErrorCode ec = e.getErrorCode();
        return ResponseEntity.status(ec.getStatus())
                .body(ErrorResponse.builder()
                        .status(ec.getStatus().value())
                        .code(ec.name())
                        .message(ec.getMessage()).build());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream().findFirst()
                .map(org.springframework.validation.FieldError::getDefaultMessage)
                .orElse("잘못된 입력입니다.");
        return  ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .status(400)
                        .code("INVALID_INPUT")
                        .message(msg).build());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleEtc(Exception e) {
        log.error("Unhandled exception", e);
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.builder()
                        .status(500)
                        .code("INTERNAL_ERROR")
                        .message("서버 내부 오류가 발생했습니다.").build());
    }
}
