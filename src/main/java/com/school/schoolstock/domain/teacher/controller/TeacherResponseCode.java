package com.school.schoolstock.domain.teacher.controller;

import com.school.schoolstock.domain.teacher.service.CouponUseResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TeacherResponseCode {

    COUPON_USE_SUCCESS(HttpStatus.OK, "COUPON_USE_SUCCESS", "쿠폰이 사용 처리되었습니다."),
    STUDENT_NOT_IN_CLASS(HttpStatus.FORBIDDEN, "STUDENT_NOT_IN_CLASS", "담당 학생의 쿠폰만 사용할 수 있습니다."),
    COUPON_USE_FAILED(HttpStatus.BAD_REQUEST, "COUPON_USE_FAILED", "쿠폰 사용 처리에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    TeacherResponseCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public static TeacherResponseCode from(CouponUseResult result) {
        if (result == CouponUseResult.STUDENT_NOT_IN_CLASS) {
            return STUDENT_NOT_IN_CLASS;
        }

        if (result == CouponUseResult.COUPON_USE_FAILED) {
            return COUPON_USE_FAILED;
        }

        return COUPON_USE_SUCCESS;
    }
}