package com.farmer.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    SUCCESS(OK, "success"),


    MEMBER_NOT_FOUND(BAD_REQUEST, "해당 회원이 존재하지 않습니다.");


    private final HttpStatus httpStatus;
    private final String message;


}
