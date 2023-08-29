package com.farmer.backend.domain.member;

import com.farmer.backend.api.controller.member.request.SearchMemberCondition;
import com.farmer.backend.api.controller.member.response.ResponseMemberInfoDto;
import com.farmer.backend.api.controller.member.response.ResponseMemberListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberQueryRepository {


    Page<Member> searchMemberList(Pageable pageable, SearchMemberCondition cond);

    //Page<Member> findAll(Pageable pageable, String sortOrderCond, SearchMemberCondition searchMemberCondition);

    void deleteMember(Long memberId);

    Page<Member> getManagerList(Pageable pageable, String sortOrderCond, SearchMemberCondition searchMemberCondition);

    Page<Member> searchManagerList(Pageable pageable, SearchMemberCondition cond);

    List<ResponseMemberListDto> memberList(String sortOrderCond, SearchMemberCondition searchMemberCond);

    ResponseMemberInfoDto memberInfo(Long memberId);
}
