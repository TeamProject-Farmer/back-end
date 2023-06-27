package com.farmer.backend.api.controller.user.join;


import com.farmer.backend.domain.member.AccountStatus;
import com.farmer.backend.domain.member.Grade;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {

    private String email;

    @Builder
    public Member toEntity(String emailKey){
        return Member.builder()
                .email(email)
                .emailAuth(emailKey)
                .password("password")
                .username("username")
                .ph("ph")
                .address("address")
                .nickname("nickname")
                .point(0L)
                .grade(Grade.NORMAL)
                .role(UserRole.USER)
                .accountStatus(AccountStatus.ACTIVITY)
                .cumulativeAmount(0L)
                .build();
    }

}
