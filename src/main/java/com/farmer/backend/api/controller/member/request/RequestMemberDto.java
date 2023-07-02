package com.farmer.backend.api.controller.member.request;

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
    private String email;
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
    public RequestMemberDto(Long id, String password,String email, String nickname, Long point, Grade grade, UserRole role, AccountStatus accountStatus, Long cumulativeAmount) {
        this.id = id;
        this.password = password;
        this.email = email;
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
                .email(email)
                .nickname(nickname)
                .point(point)
                .grade(grade)
                .role(role)
                .accountStatus(accountStatus)
                .cumulativeAmount(cumulativeAmount)
                .build();
    }


}
