package com.farmer.backend.dto.user;



import com.farmer.backend.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestJoinDto {

    private String email;
    private String password;
    private String pwcheck;
    private String username;
    private String ph;
    private String address;
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
