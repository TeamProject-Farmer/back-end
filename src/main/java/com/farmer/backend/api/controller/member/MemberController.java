package com.farmer.backend.api.controller.member;

import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.api.controller.join.EmailDto;
import com.farmer.backend.api.controller.join.RequestJoinDto;
import com.farmer.backend.api.controller.login.OAuthUserInfoDto;
import com.farmer.backend.api.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/member")
@Tag(name = "MemberController", description = "회원 페이지 API")
public class MemberController {

    private final MemberService memberService;


    /**
     * 인증 이메일 전송 (이메일 인증 버튼 클릭 시)
     */
    @ApiDocumentResponse
    @Operation(summary = "인증 이메일 전송", description = "인증 이메일을 전송합니다.")
    @PostMapping(value = "/join/mail")
    public ResponseEntity<String> emailSend(@ModelAttribute EmailDto emailDto) {

        memberService.emailStore(emailDto);
        return new ResponseEntity<>("ok", HttpStatus.OK);

    }

    /**
     * 인증 확인 URL 클릭시
     */
    @ApiDocumentResponse
    @Operation(summary = "인증 확인 URL 클릭", description = "사용자가 인증 URL을 클릭합니다.")
    @GetMapping(value = "/join/mail/checkDone")
    public ModelAndView emailCheck(@RequestParam String email){

        memberService.codeCheck(email);
        ModelAndView mailView=new ModelAndView("mailAuthentication");

        return mailView;
    }

    /**
     * 인증 확인된 이메일 체크
     */
    @ApiDocumentResponse
    @Operation(summary = "이메일 인증 여부 확인",description = "인증 확인이 된 이메일인지 체크합니다.")
    @GetMapping(value = "/join/mail/check")
    public String emailAuthentication(@RequestParam String memberEmail) {
        return memberService.emailAuth(memberEmail);
    }

    /**
     * 회원가입
     */
    @ApiDocumentResponse
    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    @PostMapping(value = "/join/membership")
    public String join (@ModelAttribute RequestJoinDto joinDto) {
        memberService.signUp(joinDto);

        return "SUCCESS";
    }

    /**
     * 소셜로그인 (KAKAO, GOOGLE, NAVER)
     */
    @GetMapping(value = "/login/oauth/{socialType}")
    public OAuthUserInfoDto oauthLogin (@PathVariable(name = "socialType") String socialType, @RequestParam String code) {

        return memberService.socialUserInfo(socialType,code);

    }


}
