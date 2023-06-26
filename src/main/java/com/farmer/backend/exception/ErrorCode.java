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
    MEMBER_FOUND(BAD_REQUEST, "해당 회원이 이미 존재합니다."),
    MEMBER_EMAIL_FOUND(BAD_REQUEST,"해당 이메일이 존재합니다."),

    NICKNAME_FOUND(BAD_REQUEST,"해당 닉네임이 존재합니다."),

    PASSWORD_NOT_EQUALS(BAD_REQUEST,"비밀번호가 일치하지 않습니다."),
    EMAIL_AUTHENTICATION(BAD_REQUEST,"인증이 완료된 이메일입니다."),
    EMAIL_YET_AUTHENTICATION(BAD_REQUEST,"이메일 인증이 완료되지 않았습니다."),
    EMAIL_NOT_AUTHENTICATION(BAD_REQUEST,"이메일 인증에 실패하였습니다."),
    PRODUCT_NOT_FOUND(BAD_REQUEST, "해당 상품이 존재하지 않습니다."),
    OPTION_NOT_FOUND(BAD_REQUEST, "해당 옵션이 존재하지 않습니다."),
    REVIEW_NOT_FOUND(BAD_REQUEST, "해당 리뷰가 존재하지 않습니다."),
    REVIEW_FOUND(BAD_REQUEST,"해당 리뷰 ID가 이미 존재합니다."),
    QNA_NOT_FOUND (BAD_REQUEST, "해당 QNA가 존재하지 않습니다."),
    NOTICE_NOT_FOUND (BAD_REQUEST, "해당 공지사항이 존재하지 않습니다."),

    FAQ_NOT_FOUND(BAD_REQUEST,"해당 자주 묻는 질문이 존재하지 않습니다."),
    FAQCATEGORY_NOT_FOUND(BAD_REQUEST,"해당 카테고리가 존재하지 않습니다."),
    ORDER_NOT_FOUND(BAD_REQUEST,"해당 ORDER가 존재하지 않습니다. " ),
    KAKAO_LOGIN_FAILURE(BAD_REQUEST,"카카오 로그인에 실패하였습니다."),

    NAVER_LOGIN_FAILURE(BAD_REQUEST,"네이버 로그인에 실패하였습니다."),

    GOOGLE_LOGIN_FAILURE(BAD_REQUEST,"구글 로그인에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;


}
