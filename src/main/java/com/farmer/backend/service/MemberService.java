package com.farmer.backend.service;

import com.farmer.backend.dto.admin.member.RequestMemberDto;
import com.farmer.backend.dto.admin.member.ResponseMemberDto;
import com.farmer.backend.dto.admin.member.SearchMemberCondition;
import com.farmer.backend.dto.user.EmailDto;
import com.farmer.backend.dto.user.MemberDto;
import com.farmer.backend.entity.Member;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.repository.admin.member.MemberQueryRepository;
import com.farmer.backend.repository.admin.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberQueryRepository memberQueryRepositoryImpl;

    /**
     * 전체 회원 리스트
     * @param pageable 페이징
     * @param sortOrderCond 정렬순서
     * @param searchMemberCondition 검색정보
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ResponseMemberDto> memberList(Pageable pageable, String sortOrderCond, SearchMemberCondition searchMemberCondition) {
        return memberQueryRepositoryImpl.findAll(pageable, sortOrderCond, searchMemberCondition).map(Member::memberList);
    }

    /**
     * 회원 단건 조회
     * @param memberId 회원 일련번호
     * @return
     */
    @Transactional(readOnly = true)
    public ResponseMemberDto findOneMember(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        return findMember.map(member -> ResponseMemberDto.getMember(member)).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    /**
     * 회원 수정
     * @param memberDto 회원 데이터
     * @return
     */
    @Transactional
    public Long updateMember(RequestMemberDto memberDto) {
        memberRepository.findById(memberDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)).updateMember(memberDto);
        return memberDto.getId();
    }

    /**
     * 회원 검색 결과 리스트
     * @param pageable 페이징
     * @param searchMemberCondition 검색정보
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ResponseMemberDto> searchMemberList(Pageable pageable, SearchMemberCondition searchMemberCondition) {
        Page<Member> memberList = memberQueryRepositoryImpl.searchMemberList(pageable, searchMemberCondition);
        return new PageImpl<>(memberList.stream().map(member -> ResponseMemberDto.getMember(member)).collect(Collectors.toList()));
    }


    /**
     * 회원 삭제(계정 상태 변경)
     * @param memberIds 회원 일련번호 배열
     */
    @Transactional
    public void deleteMember(Long[] memberIds) {
        for (Long memberId : memberIds) {
            ResponseMemberDto findMember = memberRepository.findById(memberId).map(Member::memberList).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
            memberQueryRepositoryImpl.deleteMember(memberId);
        }
    }

    /**
     * 관리자 권한 계정 리스트
     * @param pageable 페이징
     * @param sortOrderCond 정렬순서
     * @param searchMemberCondition 검색정보
     * 검색 후 정렬순서를 변경 할 수 있으므로 검색정보도 파라미터 요청
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ResponseMemberDto> managerList(Pageable pageable, String sortOrderCond, SearchMemberCondition searchMemberCondition) {
        return memberQueryRepositoryImpl.getManagerList(pageable, sortOrderCond, searchMemberCondition).map(Member::memberList);
    }

    /**
     * 관리자 권한 계정 검색 리스트
     * @param pageable 페이징
     * @param searchMemberCondition 검색정보
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ResponseMemberDto> searchManagerList(Pageable pageable, SearchMemberCondition searchMemberCondition) {
        Page<Member> memberList = memberQueryRepositoryImpl.searchManagerList(pageable, searchMemberCondition);
        return new PageImpl<>(memberList.stream().map(member -> ResponseMemberDto.getMember(member)).collect(Collectors.toList()));
    }


    //같은 회원이 이메일 인증을 2번 이상 시도할 때
    @Transactional(readOnly = true)
    public String emailAuthent(EmailDto emailDto) throws Exception{
        if (memberRepository.findByEmail(emailDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        Member member = memberRepository.save(emailDto.toEntity());

        return member.getEmail();

    }
    @Transactional(readOnly = true)
    public void signUp(MemberDto requestDto) throws Exception {

        memberRepository.findByEmail(requestDto.getEmail()).ifPresentOrElse(
                member -> {
                    if(!requestDto.getPassword().equals(requestDto.getPwcheck())){
                        log.info("비밀번호가 일치하지 않습니다.");
                    }
                    else{
                        member.updatePassword(requestDto.getPassword());
                        member.updatePwcheck(requestDto.getPwcheck());
                        member.updateUsername(requestDto.getUsername());
                        member.updatePh(requestDto.getPh());
                        member.updateAddress(requestDto.getAddress());
                        member.updateNickname(requestDto.getNickname());

                        member.encodePassword(passwordEncoder);
                    }

                },

                () -> log.info("이메일 인증이 완료되지 않았습니다.")//new Exception("이메일 인증이 완료되지 않았습니다.")

        );




    }

    @Transactional(readOnly = true)
    public void checkAuthent(String checkEmail,String checkKey) throws  Exception{
        System.out.println(checkEmail+checkKey);
        memberRepository.findByEmail(checkEmail).ifPresentOrElse(
                member -> {
                    if(member.getEmailAuth().equals(checkKey)){
                        log.info("인증 성공");
                    }
                    else{
                        log.info("인증 실패");
                    }
                },
                () -> new Exception("이메일 인증에 실패하였습니다.")
        );
    }

    @Transactional(readOnly = true)
    public void updateemailkey(String email, String emailKey) throws Exception{

        memberRepository.findByEmail(email).ifPresentOrElse(
                member -> member.updateEmailAuth(emailKey),
                () -> new Exception("이메일을 다시 입력해주세요.")
        );

    }


}
