package com.farmer.backend.dto.admin.member;

import com.farmer.backend.entity.AccountStatus;
import com.farmer.backend.entity.Grade;
import com.farmer.backend.entity.Member;
import com.farmer.backend.entity.UserRole;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RequestMemberDto {

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
