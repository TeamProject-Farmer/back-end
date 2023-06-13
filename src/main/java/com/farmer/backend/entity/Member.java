package com.farmer.backend.entity;

import com.farmer.backend.dto.admin.member.RequestMemberDto;
import com.farmer.backend.dto.admin.member.ResponseMemberDto;
import com.farmer.backend.dto.user.EmailDto;
import com.farmer.backend.dto.user.RequestJoinDto;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity{



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 80)
    @NotNull
    private String email;

    private String emailAuth;

    @NotNull
    private String password;

    @NotNull
    private String pwcheck;

    @NotNull
    @Column(length = 50)
    private String username;

    @Column(length = 20)
    private String ph;

    @Column(length = 50)
    private String address;

    @NotNull
    @Column(length = 50)
    private String nickname;

    @NotNull
    private Long point;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @NotNull
    private Long cumulativeAmount;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;
    private String socialId;
    private String refreshToken;
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
        this.pwcheck=passwordEncoder.encode(pwcheck);
    }

    public void updateEmailAuth(String updateEmailAuth) {
        this.emailAuth=updateEmailAuth;
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken=updateRefreshToken;
    }

    public ResponseMemberDto memberList() {
        return ResponseMemberDto.builder()
                .id(id)
                .email(email)
                .password(password)
                .username(username)
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

    public void updateMember(RequestMemberDto memberDto) {
        this.id = memberDto.getId();
        this.password = memberDto.getPassword();
        this.username = memberDto.getUsername();
        this.email = memberDto.getEmail();
        this.address = memberDto.getAddress();
        this.ph = memberDto.getPh();
        this.nickname = memberList().getNickname();
        this.point = memberDto.getPoint();
        this.grade = memberDto.getGrade();
        this.role = memberDto.getRole();
        this.accountStatus = memberDto.getAccountStatus();
        this.cumulativeAmount = memberDto.getCumulativeAmount();
    }

    public void joinMember(RequestJoinDto requestJoinDto){
        this.emailAuth="JoinDone";
        this.password=requestJoinDto.getPassword();
        this.pwcheck=requestJoinDto.getPwcheck();
        this.username=requestJoinDto.getUsername();
        this.ph=requestJoinDto.getPh();
        this.address=requestJoinDto.getAddress();
        this.nickname=requestJoinDto.getNickname();
    }


    public void emailSeveralRequest(EmailDto emailDto,String emailAuth){
        this.emailAuth=emailAuth;
        this.email=emailDto.getEmail();
    }
}
