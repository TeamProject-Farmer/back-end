package com.farmer.backend.entity;

import com.farmer.backend.dto.admin.member.RequestMemberDto;
import com.farmer.backend.dto.admin.member.ResponseMemberDto;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Column(length = 20, unique = true)
    private String userId;

    @NotNull
    private String password;

    @NotNull
    @Column(length = 50)
    private String username;

    @Column(length = 80)
    private String email;

    @Column(length = 255)
    private String address;

    @Column(length = 20)
    private String ph;

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

    public ResponseMemberDto memberList() {
        return Member.builder()
                .id(id)
                .userId(userId)
                .password(password)
                .username(username)
                .email(email)
                .address(address)
                .ph(ph)
                .point(point)
                .grade(grade)
                .role(role)
                .accountStatus(accountStatus)
                .cumulativeAmount(cumulativeAmount)
                .build().memberList();
    }

    public Member modifiedMember(RequestMemberDto memberDto) {
        return Member.builder()
                .userId(memberDto.getUserId())
                .password(memberDto.getPassword())
                .username(memberDto.getUsername())
                .email(memberDto.getEmail())
                .address(memberDto.getAddress())
                .ph(memberDto.getPh())
                .point(memberDto.getPoint())
                .grade(memberDto.getGrade())
                .role(memberDto.getRole())
                .accountStatus(memberDto.getAccountStatus())
                .cumulativeAmount(memberDto.getCumulativeAmount())
                .build();
    }



}
