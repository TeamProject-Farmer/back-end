package com.farmer.backend.api.controller.admin.member.response;

import com.farmer.backend.domain.member.AccountStatus;
import com.farmer.backend.domain.member.Grade;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.UserRole;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class ResponseMemberDto {

    private Long id;
    private String password;
    private String username;
    private String email;
    private String address;
    private String ph;
    private String nickname;
    private Long point;
    private Grade grade;
    private UserRole role;
    private AccountStatus accountStatus;
    private Long cumulativeAmount;

    @Builder
    public ResponseMemberDto(Long id,String password, String username, String email, String address, String ph, String nickname, Long point, Grade grade, UserRole role, AccountStatus accountStatus, Long cumulativeAmount) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.address = address;
        this.ph = ph;
        this.nickname = nickname;
        this.point = point;
        this.grade = grade;
        this.role = role;
        this.accountStatus = accountStatus;
        this.cumulativeAmount = cumulativeAmount;
    }

    // Response -> Entity
    public Member toEntity() {
        return Member.builder()
                .password(password)
                .username(username)
                .email(email)
                .address(address)
                .ph(ph)
                .nickname(nickname)
                .point(point)
                .grade(grade)
                .role(role)
                .accountStatus(accountStatus)
                .cumulativeAmount(cumulativeAmount)
                .build();
    }

    // Entity -> ResponseMemberDto
    public static ResponseMemberDto getMember(Member member) {
        return ResponseMemberDto.builder()
                .id(member.getId())
                .password(member.getPassword())
                .username(member.getUsername())
                .email(member.getEmail())
                .address(member.getAddress())
                .ph(member.getPh())
                .nickname(member.getNickname())
                .point(member.getPoint())
                .grade(member.getGrade())
                .role(member.getRole())
                .accountStatus(member.getAccountStatus())
                .cumulativeAmount(member.getCumulativeAmount())
                .build();
    }


}
