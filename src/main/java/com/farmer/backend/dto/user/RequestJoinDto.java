package com.farmer.backend.dto.user;



import com.farmer.backend.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestJoinDto {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String username;
    @NotBlank
    private String ph;
    @NotBlank
    private Long zipcode;
    @NotBlank
    private String address;
    @NotBlank
    private String detailAddress;
    @NotBlank
    private String nickname;

    @Builder
    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .username(username)
                .ph(ph)
                .zipCode(zipcode)
                .address(address)
                .detailAddress(detailAddress)
                .nickname(nickname)
                .build();
    }

}
