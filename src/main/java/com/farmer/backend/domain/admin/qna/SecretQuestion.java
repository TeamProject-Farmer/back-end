package com.farmer.backend.domain.admin.qna;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SecretQuestion {

    GENERAL("일반글"), SECRET("비밀글");

    private String name;
}
