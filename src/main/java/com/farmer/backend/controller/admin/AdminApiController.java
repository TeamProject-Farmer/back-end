package com.farmer.backend.controller.admin;

import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.dto.admin.member.RequestMemberDto;
import com.farmer.backend.dto.admin.member.ResponseMemberDto;
import com.farmer.backend.dto.admin.member.SearchMemberCondition;
import com.farmer.backend.dto.admin.member.SortOrderMemberCondition;
import com.farmer.backend.paging.PageRequest;
import com.farmer.backend.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Tag(name = "AdminController", description = "백오피스 관리자 페이지 API")
public class AdminApiController {

    private final MemberService memberService;

    /**
     * 계정 관리 페이지(관리자 권한 계정 리스트)
     */
    @ApiDocumentResponse
    @Operation(summary = "관리자 권한 계정 리스트", description = "관리자 권한을 가진 유저들을 출력합니다.")
    @GetMapping("/account")
    public Page<ResponseMemberDto> managerList(PageRequest pageRequest,
                               SortOrderMemberCondition sortOrderMemberCondition,
                               SearchMemberCondition searchMemberCondition) {

        Page<ResponseMemberDto> managerList = memberService.managerList(
                pageRequest.of(),
                sortOrderMemberCondition.getFieldName(),
                searchMemberCondition
        );
        return ResponseEntity.ok(managerList).getBody();
    }

    /**
     * 계정 관리 페이지(관리자 단건 조회)
     */
    @ApiDocumentResponse
    @Operation(summary = "관리자 단건 조회", description = "특정 관리자 정보를 열람합니다.")
    @GetMapping("/account/managers/{memberId}")
    public ResponseEntity<ResponseMemberDto> findManager(@PathVariable Long memberId) {
        ResponseMemberDto oneMember = memberService.findOneMember(memberId);
        return new ResponseEntity<>(oneMember, HttpStatus.OK);
    }

    /**
     * 계정 관리 페이지(관리자 권한 계정 검색)
     */
    @ApiDocumentResponse
    @Operation(summary = "관리자 권한 검색 리스트", description = "관리자 권한을 가진 유저들을 검색합니다.")
    @GetMapping("/account/search")
    public Page<ResponseMemberDto> searchManagerList(PageRequest pageRequest, SearchMemberCondition searchMemberCondition) {
        Page<ResponseMemberDto> searchList = memberService.searchManagerList(pageRequest.of(), searchMemberCondition);
        return ResponseEntity.ok(searchList).getBody();
    }

    /**
     * 계정 관리 페이지(관리자 권한 계정 수정)
     */
    @ApiDocumentResponse
    @Operation(summary = "관리자 권한 계정 수정", description = "관리자 권한을 가진 유저들을 수정합니다.")
    @PostMapping("/account/managers-update")
    public Long updateManager(@ModelAttribute RequestMemberDto memberDto) {
        return memberService.updateMember(memberDto);
    }


    /**
     * 회원 관리 페이지(회원 전체 리스트)
     */
    @ApiDocumentResponse
    @Operation(summary = "회원 전체 리스트", description = "회원 전체 리스트를 출력합니다.")
    @GetMapping("/member-list")
    public Page<ResponseMemberDto> memberList(PageRequest pageRequest,
                                              SortOrderMemberCondition sortOrderMemberCondition,
                                              SearchMemberCondition searchMemberCondition) {

        Page<ResponseMemberDto> memberList = memberService.memberList(
                pageRequest.of(),
                sortOrderMemberCondition.getFieldName(),
                searchMemberCondition
        );
        return ResponseEntity.ok(memberList).getBody();
    }

    /**
     * 회원 관리 페이지(회원 단건 조회)
     */
    @ApiDocumentResponse
    @Operation(summary = "회원 단건 조회", description = "특정 회원 정보를 열람합니다.")
    @GetMapping("/members/{memberId}")
    public ResponseEntity<ResponseMemberDto> findMember(@PathVariable Long memberId) {
        ResponseMemberDto oneMember = memberService.findOneMember(memberId);
        return new ResponseEntity<>(oneMember, HttpStatus.OK);
    }

    /**
     * 회원 관리 페이지(회원 수정)
     */
    @ApiDocumentResponse
    @Operation(summary = "회원 수정", description = "회원 정보를 수정합니다.")
    @PostMapping("/members/update")
    public Long updateMember(@ModelAttribute RequestMemberDto memberDto) {
        return memberService.updateMember(memberDto);
    }

    /**
     * 회원 관리 페이지(회원 삭제)
     */
    @ApiDocumentResponse
    @Operation(summary = "회원 삭제", description = "회원을 삭제합니다.")
    @PostMapping("/members/delete")
    public void deleteMember(@RequestParam(value = "member") Long memberIds[]) {
        memberService.deleteMember(memberIds);
    }

    /**
     * 회원 관리 페이지(회원 검색(이름, 아이디))
     */
    @ApiDocumentResponse
    @Operation(summary = "회원 검색 리스트", description = "회원 검색 리스트를 출력합니다.")
    @GetMapping("/member-list/search")
    public Page<ResponseMemberDto> searchMemberList(PageRequest pageRequest, SearchMemberCondition searchMemberCondition) {
        Page<ResponseMemberDto> searchList = memberService.searchMemberList(pageRequest.of(), searchMemberCondition);
        return ResponseEntity.ok(searchList).getBody();
    }
}
