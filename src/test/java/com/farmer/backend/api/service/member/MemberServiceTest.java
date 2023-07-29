package com.farmer.backend.api.service.member;

import com.farmer.backend.api.controller.login.ResponseLoginMemberDto;
import com.farmer.backend.api.controller.member.request.RequestMemberDto;
import com.farmer.backend.api.controller.member.request.RequestMemberProfileDto;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.domain.memberscoupon.MemberCouponRepository;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

        Member member = memberRepository.findByEmail("kce2360@naver.com").orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        RequestMemberProfileDto requestMemberProfileDto = new RequestMemberProfileDto("0000","챈잉1");

        member.updateProfile(requestMemberProfileDto);
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
//        assertThat(point).isZero();
    }
}