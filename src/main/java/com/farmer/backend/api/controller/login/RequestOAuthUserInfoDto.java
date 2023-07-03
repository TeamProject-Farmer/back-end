package com.farmer.backend.api.controller.login;

import com.farmer.backend.domain.member.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class RequestOAuthUserInfoDto {

    private SocialType socialType;
    private String socialId;
    private String email;
    private String nickname;
    private String accessToken;
    private String refreshToken;
    public RequestOAuthUserInfoDto(String socialId, SocialType socialType, String email, String nickname, String accessToken, String refreshToken){
        this.socialId=socialId;
        this.socialType=socialType;
        this.email=email;
        this.nickname=nickname;
        this.accessToken=accessToken;
        this.refreshToken=refreshToken;
    }

    public Member toEntity(RequestOAuthUserInfoDto userInfo){
        return Member.builder()
                .email(userInfo.email)
                .password(String.valueOf(UUID.randomUUID()))
                .nickname(userInfo.nickname)
                .point(0L)
                .grade(Grade.NORMAL)
                .role(UserRole.USER)
                .accountStatus(AccountStatus.ACTIVITY)
                .accessToken(userInfo.accessToken)
                .refreshToken(userInfo.refreshToken)
                .cumulativeAmount(0L)
                .socialType(userInfo.socialType)
                .socialId(userInfo.socialId)
                .build();

    }
}
