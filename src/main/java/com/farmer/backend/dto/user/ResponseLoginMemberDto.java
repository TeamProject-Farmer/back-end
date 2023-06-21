package com.farmer.backend.dto.user;

import com.farmer.backend.entity.Grade;
import com.farmer.backend.entity.Member;
import com.farmer.backend.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder

public class ResponseLoginMemberDto {

    private String email;
    private String password;
    private String username;
    private Long zipCode;
    private String address;
    private String detailAddress;
    private String ph;
    private String nickname;
    private Long point;
    private Grade grade;
    private UserRole role;

    private String accessToken;
    private String refreshToken;

    public static ResponseLoginMemberDto getLoginMember(Member member){
        return ResponseLoginMemberDto.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .username(member.getUsername())
                .zipCode(member.getZipCode())
                .address(member.getAddress())
                .detailAddress(member.getDetailAddress())
                .ph(member.getPh())
                .nickname(member.getNickname())
                .point(member.getPoint())
                .grade(member.getGrade())
                .role(member.getRole())
                .accessToken(member.getAccessToken())
                .refreshToken(member.getRefreshToken())
                .build();

    }
}
