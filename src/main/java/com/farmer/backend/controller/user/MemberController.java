package com.farmer.backend.controller.user;

import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.dto.user.EmailDto;
import com.farmer.backend.dto.user.RequestJoinDto;
import com.farmer.backend.service.MemberService;
import com.farmer.backend.service.user.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/member")
@Tag(name = "MemberController", description = "회원 페이지 API")
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;


    /**
     * 인증 이메일 전송 (이메일 인증 버튼 클릭 시)
     */
    @ApiDocumentResponse
    @Operation(summary = "인증 이메일 전송", description = "인증 이메일을 전송합니다.")
    @PostMapping("/join/mail")
    @ResponseStatus(HttpStatus.OK)
    public void emailSend(@ModelAttribute EmailDto emailDto) {

        String emailKey = mailService.sendAuthMail(emailDto.getEmail());
        memberService.emailStore(emailDto,emailKey);

    }

    /**
     * 인증 확인 URL 클릭시
     */
    @ApiDocumentResponse
    @Operation(summary = "인증 확인 URL 클릭", description = "이메일 인증을 합니다.")
    @GetMapping("/join/mail/check")
    public String EmailCheck(@RequestParam Map<String, String> mailInfo){
        return memberService.codeCheck(mailInfo);
    }

    /**
     * 회원가입
     */
    @ApiDocumentResponse
    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    @PostMapping("/join/membership")
    public void join (@ModelAttribute RequestJoinDto joinDto) {
        memberService.signUp(joinDto);
    }

}
