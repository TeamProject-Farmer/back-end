package com.farmer.backend.api.controller.member;
import com.farmer.backend.api.controller.SortOrderCondition;
import com.farmer.backend.api.controller.login.ResponseLoginMemberDto;
import com.farmer.backend.api.controller.member.request.RequestMemberProfileDto;
import com.farmer.backend.api.controller.member.request.SearchMemberCondition;
import com.farmer.backend.api.controller.member.response.ResponseMemberDto;
import com.farmer.backend.api.controller.member.response.ResponseMemberListDto;
import com.farmer.backend.api.service.member.MemberService;
import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.domain.member.UserRole;
import com.farmer.backend.login.general.MemberAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public String profileUpdate(@AuthenticationPrincipal MemberAdapter memberAdapter,
                                                @ModelAttribute RequestMemberProfileDto requestMemberProfileDto){

        String memberEmail = memberAdapter.getMember().getEmail();

        if(requestMemberProfileDto.getPassword() == null){
            RequestMemberProfileDto socialMember = new RequestMemberProfileDto(UUID.randomUUID().toString(), requestMemberProfileDto.getNickname());
            requestMemberProfileDto=socialMember;
        }

        return memberService.profileUpdate(memberEmail,requestMemberProfileDto);
    }

    /**
     * 회원 적립금 조회
     */
    @ApiDocumentResponse
    @Operation(summary = "회원 적립금", description = "회원 적립금을 조회합니다.")
    @PostMapping(value = "/point")
    public ResponseEntity memberPoint(@AuthenticationPrincipal MemberAdapter member) {
        return ResponseEntity.ok(memberService.getPoint(member.getUsername()));
    }

    /**
     *  전체 회원 목록 조회 (admin)
     */
    @ApiDocumentResponse
    @Operation(summary = "전체 회원 목록 조회" , description = "파머 회원의 목록을 조회합니다.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/admin/memberList")
    public List<ResponseMemberListDto> farmerMemberList(@AuthenticationPrincipal MemberAdapter memberAdapter,
                                                        String sortOrderCond,
                                                        String searchMemberCond){

        return memberService.memberList(memberAdapter.getMember(),sortOrderCond,searchMemberCond);

    }


}
