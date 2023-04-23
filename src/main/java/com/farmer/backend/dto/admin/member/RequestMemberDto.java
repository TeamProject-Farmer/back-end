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
public class RequestMemberDto {

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


    // RequestDto -> Entity
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

    // 회원가입(RequestMemberDto -> Entity)
    public Member signup() {
        return Member.builder()
                .userId(userId)
                .password(password)
                .username(username)
                .email(email)
                .address(address)
                .ph(ph)
                .point(0L)
                .grade(grade)
                .role(UserRole.USER)
                .accountStatus(AccountStatus.ACTIVITY)
                .cumulativeAmount(0L)
                .build();
    }


}
