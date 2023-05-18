package com.farmer.backend.repository.admin.member;


import com.farmer.backend.dto.admin.member.SearchMemberCondition;
import com.farmer.backend.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberQueryRepository {


    Page<Member> searchMemberList(Pageable pageable, SearchMemberCondition cond);

    Page<Member> findAll(Pageable pageable, String sortOrderCond, SearchMemberCondition searchMemberCondition);

    void deleteMember(Long memberId);

    Page<Member> getManagerList(Pageable pageable, String sortOrderCond, SearchMemberCondition searchMemberCondition);

    Page<Member> searchManagerList(Pageable pageable, SearchMemberCondition cond);
}
