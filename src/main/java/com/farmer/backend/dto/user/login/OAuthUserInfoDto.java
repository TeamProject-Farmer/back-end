package com.farmer.backend.dto.user.login;

import com.farmer.backend.entity.*;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OAuthUserInfoDto {

    private SocialType socialType;
    private String socialId;
    private String email;

    private String accessToken;
    private String refreshToken;

    public static OAuthUserInfoDto getUserInfo(Member member){
        return OAuthUserInfoDto.builder()
                .email(member.getEmail())
                .accessToken(member.getAccessToken())
                .refreshToken(member.getRefreshToken())
                .socialType(SocialType.valueOf(member.getSocialType().name()))
                .socialId(member.getSocialId())
                .build();
    }
    public OAuthUserInfoDto(String socialId, SocialType socialType, String email, String accessToken, String refreshToken){
        this.socialId=socialId;
        this.socialType=socialType;
        this.email=email;
        this.accessToken=accessToken;
        this.refreshToken=refreshToken;
    }

    public Member toEntity(OAuthUserInfoDto userInfo){
        return Member.builder()
                .email(userInfo.email)
                .password("password")
                .username("username")
                .nickname("nickname")
                .ph("ph")
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
