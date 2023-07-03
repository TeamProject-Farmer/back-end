package com.farmer.backend.api.controller.login;

import com.farmer.backend.domain.member.*;
import lombok.*;

import java.util.Optional;
import java.util.UUID;

import static org.eclipse.jdt.core.compiler.CharOperation.lastIndexOf;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResponseOAuthUserInfoDto {

    private String socialId;
    private String email;
    private String nickname;
    private Long point;
    private Grade grade;
    private UserRole userRole;
    private Long memberCoupon;
    private Long cumulativeAmount;
    private String accessToken;
    private String refreshToken;

    public static ResponseOAuthUserInfoDto getUserInfo(Optional<Member> member, Long memberCoupon){
        return ResponseOAuthUserInfoDto.builder()
                .email(member.get().getEmail().substring(0,member.get().getEmail().lastIndexOf("[")))
                .nickname(member.get().getNickname())
                .grade(member.get().getGrade())
                .point(member.get().getPoint())
                .userRole(member.get().getRole())
                .memberCoupon(memberCoupon)
                .cumulativeAmount(member.get().getCumulativeAmount())
                .accessToken(member.get().getAccessToken())
                .refreshToken(member.get().getRefreshToken())
                .socialId(member.get().getSocialId())
                .build();
    }

}
