package com.farmer.backend.api.controller.user.join;

import com.farmer.backend.domain.member.Member;
import com.mysema.commons.lang.Assert;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RequestJoinDto {

    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String nickname;

    @Builder
    public RequestJoinDto(String email ,String password,String nickname) {

        Assert.hasText(email, "이메일을 입력해주세요.");
        Assert.hasText(password, "비밀번호를 입력해주세요.");
        Assert.hasText(nickname, "닉네임을 입력해주세요.");

        this.email = email;
        this.password = password;
        this.nickname =  nickname;
    }

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }

}
