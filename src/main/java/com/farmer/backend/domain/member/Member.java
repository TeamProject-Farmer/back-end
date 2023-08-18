package com.farmer.backend.domain.member;

import com.farmer.backend.api.controller.member.request.RequestMemberProfileDto;
import com.farmer.backend.api.controller.order.request.RequestOrderInfoDto;
import com.farmer.backend.domain.BaseTimeEntity;
import com.farmer.backend.api.controller.member.request.RequestMemberDto;
import com.farmer.backend.api.controller.member.response.ResponseMemberDto;
import com.farmer.backend.api.controller.join.EmailDto;
import com.farmer.backend.api.controller.join.RequestJoinDto;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

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

    private String accessToken;
    private String refreshToken;

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    public void updateEmailAuth(String updateEmailAuth) {
        this.emailAuth=updateEmailAuth;
    }

    public void updateToken(String updateRefreshToken,String updateAccessToken) {
        this.refreshToken=updateRefreshToken;
        this.accessToken=updateAccessToken;
    }

    public void updateProfile(String password, String nickname){
        this.password=password;
        this.nickname=nickname;
    }

    public ResponseMemberDto memberList() {
        return ResponseMemberDto.builder()
                .id(id)
                .email(email)
                .password(password)
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
        this.email = memberDto.getEmail();
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
        this.nickname=requestJoinDto.getNickname();
    }
    public void emailSeveralRequest(EmailDto emailDto,String emailAuth){
        this.emailAuth=emailAuth;
        this.email=emailDto.getEmail();
    }

    public void deductionPoint(Member member, RequestOrderInfoDto orderInfoDto) {
        this.id = member.getId();
        if (orderInfoDto.getPoint() <= member.getPoint()) {
            this.point -= orderInfoDto.getPoint();
        }
    }
}
