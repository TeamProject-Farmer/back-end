package com.farmer.backend.service.admin;

import com.farmer.backend.dto.admin.member.RequestMemberDto;
import com.farmer.backend.dto.admin.member.ResponseMemberDto;
import com.farmer.backend.dto.admin.member.SearchMemberCondition;
import com.farmer.backend.entity.Member;
import com.farmer.backend.repository.admin.member.MemberQueryRepository;
import com.farmer.backend.repository.admin.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepositoryImpl;

    //전체 회원 리스트(최근 가입 순)
    @Transactional(readOnly = true)
    public Page<ResponseMemberDto> memberList(@PageableDefault(sort = "member_id", direction = Sort.Direction.DESC) Pageable pageable) {
        return memberRepository.findAll(pageable).map(Member::memberList);
    }

    //회원 단건 조회
    @Transactional(readOnly = true)
    public ResponseMemberDto findOneMember(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        return findMember.map(member -> ResponseMemberDto.getMember(member)).orElseThrow(() -> new NoSuchElementException());
    }

    //회원 저장
    @Transactional
    public Long save(RequestMemberDto memberDto) {
        return memberRepository.save(memberDto.signup()).getId();
    }

    //회원 수정
    @Transactional
    public Long update(RequestMemberDto memberDto) {
        Optional<Member> findMember = memberRepository.findById(memberDto.getId());
        findMember.orElseThrow(() -> new RuntimeException()).modifiedMember(memberDto);
        return memberDto.getId();
    }

    //회원 삭제
    @Transactional
    public Long delete(RequestMemberDto memberDto) {
        Optional<Member> findMember = memberRepository.findById(memberDto.getId());
        memberRepository.delete(findMember.orElseThrow(() -> new NoSuchElementException()));
        return memberDto.getId();
    }

    //회원 검색(아이디, 이름)
    @Transactional(readOnly = true)
    public List<ResponseMemberDto> searchList(SearchMemberCondition cond) {
        List<Member> memberList = memberQueryRepositoryImpl.searchMemberList(cond);
        return memberList.stream().map(member -> ResponseMemberDto.getMember(member)).collect(Collectors.toList());
    }


}
