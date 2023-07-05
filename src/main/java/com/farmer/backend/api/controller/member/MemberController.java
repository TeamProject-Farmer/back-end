package com.farmer.backend.api.controller.member;
import com.farmer.backend.api.controller.login.ResponseLoginMemberDto;
import com.farmer.backend.api.controller.member.request.RequestMemberProfileDto;
import com.farmer.backend.api.service.member.MemberService;
import com.farmer.backend.config.ApiDocumentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/member")
@Tag(name = "MemberController", description = "회원 페이지 API")
public class MemberController {

    private final MemberService memberService;

    /**
     * 마이페이지 회원 프로필 수정
     */

    @ApiDocumentResponse
    @Operation(summary = "회원 프로필 수정",description = "회원 프로필을 수정합니다.")
    @PostMapping(value = "mypage/profile")
    public ResponseLoginMemberDto profileUpdate(Authentication authentication,
                                                @ModelAttribute RequestMemberProfileDto requestMemberProfileDto){
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        return memberService.profileUpdate(userDetails.getUsername(),requestMemberProfileDto);
    }


}
