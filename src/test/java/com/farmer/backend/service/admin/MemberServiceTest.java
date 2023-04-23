package com.farmer.backend.service.admin;

import com.farmer.backend.dto.admin.member.RequestMemberDto;
import com.farmer.backend.dto.admin.member.ResponseMemberDto;
import com.farmer.backend.entity.AccountStatus;
import com.farmer.backend.entity.Grade;
import com.farmer.backend.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Slf4j
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("회원가입 테스트")
    void signup() {
        //given
        RequestMemberDto savedMember = new RequestMemberDto(
                null,
                "test11",
                "1234",
                "테스트1",
                "test11@naver.com",
                "서울시 은평구",
                "01012341234",
                0L,
                Grade.NORMAL,
                UserRole.USER,
                AccountStatus.ACTIVITY,
                0L
        );
        //when
        Long savedId = memberService.save(savedMember);
        ResponseMemberDto findMember = memberService.findOneMember(savedId);
        //then
        assertThat(findMember.getUserId()).isEqualTo(savedMember.getUserId());
    }

}