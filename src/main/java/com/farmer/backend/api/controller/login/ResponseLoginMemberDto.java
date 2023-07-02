package com.farmer.backend.api.controller.login;

import com.farmer.backend.domain.member.Grade;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder

public class ResponseLoginMemberDto {

    private String email;
    private String nickname;
    private Long point;
    private Grade grade;
    private UserRole role;

    private String accessToken;
    private String refreshToken;

    public static ResponseLoginMemberDto getLoginMember(Member member){
        return ResponseLoginMemberDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .point(member.getPoint())
                .grade(member.getGrade())
                .role(member.getRole())
                .accessToken(member.getAccessToken())
                .refreshToken(member.getRefreshToken())
                .build();

    }
}
