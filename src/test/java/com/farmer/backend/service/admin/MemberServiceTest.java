package com.farmer.backend.service.admin;

import com.farmer.backend.api.service.admin.BoardService;
import com.farmer.backend.api.service.admin.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@Transactional
@SpringBootTest
@Slf4j
@Commit
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    BoardService boardService;
    @Autowired
    EntityManager em;

//    @Test
//    void qnaUpdate(){
//
//        boardService.updateQnA()
//
//    }
}