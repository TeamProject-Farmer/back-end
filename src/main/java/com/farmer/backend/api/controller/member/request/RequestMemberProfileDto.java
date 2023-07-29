package com.farmer.backend.api.controller.member.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestMemberProfileDto {


    private String password;
    private String nickname;

    @Builder
    public RequestMemberProfileDto(String password, String nickname) {

        this.password = password;
        this.nickname =  nickname;
    }
}
