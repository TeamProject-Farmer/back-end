package com.farmer.backend.api.service.member;

import com.farmer.backend.api.controller.user.login.ResponseLoginMemberDto;
import com.farmer.backend.api.controller.user.member.request.RequestMemberProfileDto;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.domain.memberscoupon.MemberCouponRepository;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberCouponRepository memberCouponRepository;


    @Test
    @DisplayName("마이페이지 프로필 수정")
    void profileUpdate() {

        Member member = memberRepository.findByEmail("codms7020@naver.com[NAVER]").orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        RequestMemberProfileDto requestMemberProfileDto = new RequestMemberProfileDto(null,"챈s");
        String nickname = requestMemberProfileDto.getNickname();
        String socialId = member.getSocialId();

        if(requestMemberProfileDto.getPassword() == null){
            RequestMemberProfileDto socialMember = new RequestMemberProfileDto(UUID.randomUUID().toString(),requestMemberProfileDto.getNickname());
            requestMemberProfileDto=socialMember;
        }

        if(!member.getNickname().equals(nickname) && memberRepository.findByNickname(nickname).isPresent()){
            throw new CustomException(ErrorCode.NICKNAME_FOUND);
        }
        else if (!socialId.equals(null)) {
            member.updateProfile(member.getPassword(),requestMemberProfileDto.getNickname());
        }
        else{
            member.updateProfile(requestMemberProfileDto.getPassword(),requestMemberProfileDto.getNickname());
        }

        Long coupon= memberCouponRepository.countByMemberId(member.getId());

        ResponseLoginMemberDto responseLoginMemberDto= ResponseLoginMemberDto.getLoginMember(member, coupon);

        log.info(responseLoginMemberDto.getEmail());
        log.info(responseLoginMemberDto.getNickname());


    }

    @Test
    @DisplayName("회원 적립금 조회")
    void getPoint() {
        Member member = memberRepository.findByEmail("kce2360@naver.com").orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Long point = memberRepository.findByEmail(member.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)).getPoint();
        assertThat(point).isEqualTo(member.getPoint());
    }
}