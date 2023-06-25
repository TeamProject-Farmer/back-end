package com.farmer.backend.dto.user.join;


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
