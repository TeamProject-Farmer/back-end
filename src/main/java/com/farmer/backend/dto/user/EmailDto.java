package com.farmer.backend.dto.user;


import com.farmer.backend.entity.AccountStatus;
import com.farmer.backend.entity.Grade;
import com.farmer.backend.entity.Member;
import com.farmer.backend.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {

    @NotBlank(message = "이메일")
    private String email;

    @Builder
    public Member toEntity(){
        return Member.builder()
                .email(email)
                .emailAuth("emailAuth")
                .password("password")
                .pwcheck("pwcheck")
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
