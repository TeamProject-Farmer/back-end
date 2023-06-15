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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @PostMapping(value = "/join/mail",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> emailSend(@Valid EmailDto emailDto) {

        String emailKey = mailService.sendAuthMail(emailDto.getEmail());
        memberService.emailStore(emailDto,emailKey);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    /**
     * 인증 확인 URL 클릭시
     */
    @ApiDocumentResponse
    @Operation(summary = "인증 확인 URL 클릭", description = "사용자가 인증 URL을 클릭합니다.")
    @GetMapping(value = "/join/mail/checkDone",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void emailCheck(@RequestParam String email){
        memberService.codeCheck(email);
    }

    /**
     * 인증 확인된 이메일 체크
     */
    @ApiDocumentResponse
    @Operation(summary = "이메일 인증 여부 확인",description = "인증 확인이 된 이메일인지 체크합니다.")
    @GetMapping(value = "join/mail/check",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String emailAuthentication(@RequestParam String memberEmail) {
        return memberService.emailAuth(memberEmail);
    }

        /**
         * 회원가입
         */
    @ApiDocumentResponse
    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    @PostMapping(value = "/join/membership",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void join (@ModelAttribute RequestJoinDto joinDto) {
        memberService.signUp(joinDto);
    }

}
