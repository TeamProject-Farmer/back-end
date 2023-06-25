package com.farmer.backend.dto.user.join;

import com.farmer.backend.entity.Member;
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
    private String username;
    @NotNull
    private String ph;
    @NotNull
    private Long zipcode;
    @NotNull
    private String address;
    @NotNull
    private String detailAddress;
    @NotNull
    private String nickname;

    @Builder
    public RequestJoinDto(String email ,String password, String username , String ph , String address, Long zipcode, String detailAddress,String nickname) {

        Assert.hasText(email, "이메일을 입력해주세요.");
        Assert.hasText(password, "비밀번호를 입력해주세요.");
        Assert.hasText(username, "이름을 입력해주세요.");
        Assert.hasText(ph, "휴대폰 번호를 입력해주세요.");
        Assert.hasText(address, "주소를 입력해주세요.");
        Assert.notNull(zipcode,"우편번호를 입력해주세요.");
        Assert.hasText(detailAddress, "상세주소를 입력해주세요.");
        Assert.hasText(nickname, "닉네임을 입력해주세요.");

        this.email = email;
        this.password = password;
        this.username = username;
        this.address = address;
        this.ph = ph;
        this.nickname =  nickname;
        this.detailAddress=detailAddress;
        this.zipcode=zipcode;
    }

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
