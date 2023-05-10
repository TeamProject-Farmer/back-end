package com.farmer.backend.controller.user;

import com.farmer.backend.dto.user.EmailDto;
import com.farmer.backend.dto.user.MemberDto;
import com.farmer.backend.repository.admin.member.MemberRepository;
import com.farmer.backend.service.MemberService;
import com.farmer.backend.service.user.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;
    private final MemberRepository memberRepository;



    //회원가입 페이지에서 회원가입 버튼을 눌렀을 때
    @PostMapping("member/mail")
    @ResponseStatus(HttpStatus.OK)
    public void emailauthent(@Valid @RequestBody EmailDto emailDto) throws Exception {

        String emailkey = mailService.sendAuthMail(memberService.emailAuthent(emailDto));

        memberService.updateemailkey(emailDto.getEmail(),emailkey);

    }

    //인증 메일에 있는 인증 확인 url 을 눌렀을 때
    @GetMapping("member")
    public void checkAuthent(HttpServletRequest request) throws Exception{
        String checkEmail=request.getParameter("email");
        String checkKey=request.getParameter("authKey");
        memberService.checkAuthent(checkEmail,checkKey);


    }

    //회원가입하기 버튼을 눌렀을 때
    @PostMapping("member/join")
    @ResponseStatus(HttpStatus.OK)
    public void join(@Valid @RequestBody MemberDto request) throws Exception {
        memberService.signUp(request);
    }

    @GetMapping("member/jwt-test")
    public String jwtTest(){
        return "jwtTest 요청 성공";
    }

}
