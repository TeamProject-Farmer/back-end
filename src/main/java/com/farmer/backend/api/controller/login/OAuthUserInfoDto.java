package com.farmer.backend.api.controller.login;

import com.farmer.backend.domain.member.*;
import lombok.*;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OAuthUserInfoDto {

    private SocialType socialType;
    private String socialId;
    private String email;
    private String nickname;
    private Long point;
    private Grade grade;
    private UserRole userRole;
    private AccountStatus accountStatus;
    private Long memberCoupon;
    private Long cumulativeAmount;
    private String accessToken;
    private String refreshToken;

    public static OAuthUserInfoDto getUserInfo(Optional<Member> member,Long memberCoupon){
        return OAuthUserInfoDto.builder()
                .email(member.get().getEmail())
                .nickname(member.get().getNickname())
                .grade(member.get().getGrade())
                .point(member.get().getPoint())
                .userRole(member.get().getRole())
                .memberCoupon(memberCoupon)
                .accountStatus(member.get().getAccountStatus())
                .cumulativeAmount(member.get().getCumulativeAmount())
                .accessToken(member.get().getAccessToken())
                .refreshToken(member.get().getRefreshToken())
                .socialType(SocialType.valueOf(member.get().getSocialType().name()))
                .socialId(member.get().getSocialId())
                .build();
    }
    public OAuthUserInfoDto(String socialId, SocialType socialType, String email, String nickname, String accessToken, String refreshToken){
        this.socialId=socialId;
        this.socialType=socialType;
        this.email=email;
        this.nickname=nickname;
        this.accessToken=accessToken;
        this.refreshToken=refreshToken;
    }

    public Member toEntity(OAuthUserInfoDto userInfo){
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
