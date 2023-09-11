package com.farmer.backend.api.controller.user.member.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseMemberTokenDto {

    private String accessToken;
    private String refreshToken;

    public ResponseMemberTokenDto(String accessToken, String refreshToken){
        this.accessToken=accessToken;
        this.refreshToken=refreshToken;
    }
}
