package com.farmer.backend.api.controller.member;
import com.farmer.backend.api.controller.login.ResponseLoginMemberDto;
import com.farmer.backend.api.controller.member.request.RequestMemberProfileDto;
import com.farmer.backend.api.service.member.MemberService;
import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.login.general.MemberAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public ResponseLoginMemberDto profileUpdate(@AuthenticationPrincipal MemberAdapter memberAdapter,
                                                @ModelAttribute RequestMemberProfileDto requestMemberProfileDto){

        String memberEmail = memberAdapter.getMember().getEmail();

        if(requestMemberProfileDto.getPassword() == null){
            RequestMemberProfileDto socialMember = new RequestMemberProfileDto(UUID.randomUUID().toString(), requestMemberProfileDto.getNickname());
            requestMemberProfileDto=socialMember;
        }

        return memberService.profileUpdate(memberEmail,requestMemberProfileDto);
    }

}
