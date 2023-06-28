package com.farmer.backend.api.controller.admin.member.request;

import com.farmer.backend.domain.member.AccountStatus;
import com.farmer.backend.domain.member.Grade;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.UserRole;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RequestMemberDto {

    private Long id;
    @NotBlank(message = "비밀번호")
    private String password;
    @NotNull(message = "이름")
    private String username;
    private String email;
    private String address;
    private String ph;
    @NotBlank(message = "닉네임")
    private String nickname;
    @NotNull(message = "적립금")
    private Long point;
    @NotNull(message = "회원등급")
    private Grade grade;
    @NotNull(message = "회원권한")
    private UserRole role;
    @NotNull(message = "계정상태")
    private AccountStatus accountStatus;
    @NotNull(message = "총 구매금액")
    private Long cumulativeAmount;

    @Builder
    public RequestMemberDto(Long id, String password, String username, String email, String address, String ph, String nickname, Long point, Grade grade, UserRole role, AccountStatus accountStatus, Long cumulativeAmount) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
        this.address = address;
        this.ph = ph;
        this.nickname =  nickname;
        this.point = point;
        this.grade = grade;
        this.role = role;
        this.accountStatus = accountStatus;
        this.cumulativeAmount = cumulativeAmount;
    }

    // RequestDto -> Entity
    public Member toEntity() {
        return Member.builder()
                .id(id)
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


}
