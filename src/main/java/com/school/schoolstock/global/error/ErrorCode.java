package com.school.schoolstock.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    POINT_NOT_ENOUGH(HttpStatus.CONFLICT, "보유 포인트가 부족합니다."),
    HOLDING_NOT_ENOUGH(HttpStatus.CONFLICT, "보유 주식량보다 많은 매도는 할 수 없습니다."),
    CANNOT_SELL_ON_PUBLICATION(HttpStatus.CONFLICT, "발행 잔량이 남아 매도할 수 없습니다."),
    ORDER_PRICE_TOO_LOW(HttpStatus.CONFLICT, "현재가격 이상으로만 매수 주문이 가능합니다."),
    ORDER_NOT_CANCELABLE(HttpStatus.CONFLICT, "취소할 수 없는 주문입니다."),
    COUPON_LIMIT_EXCEEDED(HttpStatus.CONFLICT, "보유 쿠폰 한도(3개)를 초과했습니다."),
    COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, "쿠폰을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
