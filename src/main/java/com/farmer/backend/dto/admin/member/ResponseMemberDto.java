package com.farmer.backend.dto.admin.member;

import com.farmer.backend.entity.AccountStatus;
import com.farmer.backend.entity.Grade;
import com.farmer.backend.entity.Member;
import com.farmer.backend.entity.UserRole;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class ResponseMemberDto {

    private Long id;
    private String userId;
    private String password;
    private String username;
    private String email;
    private String address;
    private String ph;
    private Long point;
    private Grade grade;
    private UserRole role;
    private AccountStatus accountStatus;
    private Long cumulativeAmount;

    // Response -> Entity
    public Member toEntity() {
        return Member.builder()
                .userId(userId)
                .password(password)
                .username(username)
                .email(email)
                .address(address)
                .ph(ph)
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
                .userId(member.getUserId())
                .password(member.getPassword())
                .username(member.getUsername())
                .email(member.getEmail())
                .address(member.getAddress())
                .ph(member.getPh())
                .point(member.getPoint())
                .grade(member.getGrade())
                .role(member.getRole())
                .accountStatus(member.getAccountStatus())
                .cumulativeAmount(member.getCumulativeAmount())
                .build();
    }

}
