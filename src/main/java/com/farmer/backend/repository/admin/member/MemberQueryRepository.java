package com.farmer.backend.repository.admin.member;

import com.farmer.backend.dto.admin.member.SearchMemberCondition;
import com.farmer.backend.entity.Member;

import java.util.List;

public interface MemberQueryRepository {


    List<Member> searchMemberList(SearchMemberCondition cond);
}
