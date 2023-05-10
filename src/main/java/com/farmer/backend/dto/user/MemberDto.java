package com.farmer.backend.dto.user;



import com.farmer.backend.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    @NotBlank(message = "이메일")
    private String email;

    @NotBlank(message = "비밀번호")
    private String password;

    @NotBlank(message = "비밀번호 확인")
    private String pwcheck;
    @NotBlank
    private String username;

    @NotBlank(message = "전화번호")
    private String ph;
    @NotBlank(message = "전화번호")
    private String address;

    @NotBlank(message = "별명")
    private String nickname;

    @Builder
    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .pwcheck(pwcheck)
                .username(username)
                .ph(ph)
                .address(address)
                .nickname(nickname)
                .build();
    }

}
