package com.farmer.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    SUCCESS(OK, "success"),

    MEMBER_NOT_FOUND(BAD_REQUEST, "해당 회원이 존재하지 않습니다."),

    PRODUCT_NOT_FOUND(BAD_REQUEST,"해당 제품이 존재하지 않습니다."),

    REVIEW_NOT_FOUND(BAD_REQUEST, "해당 리뷰가 존재하지 않습니다."),

    REVIEW_FOUND(BAD_REQUEST,"해당 리뷰 ID가 이미 존재합니다."),
    QNA_NOT_FOUND (BAD_REQUEST, "해당 QNA가 존재하지 않습니다."),
    ORDER_NOT_FOUND(BAD_REQUEST,"해당 ORDER가 존재하지 않습니다. " );

    private final HttpStatus httpStatus;
    private final String message;


}
