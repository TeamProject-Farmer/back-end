package com.farmer.backend.service;

import com.farmer.backend.dto.admin.member.RequestMemberDto;
import com.farmer.backend.dto.admin.member.ResponseMemberDto;
import com.farmer.backend.dto.admin.member.SearchMemberCondition;
import com.farmer.backend.dto.user.EmailDto;
import com.farmer.backend.dto.user.RequestJoinDto;
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


import java.util.Map;
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


    /**
     * 이메일 인증 시도한 이메일 저장
     * @param emailDto 이메일 데이터
     * @return 인증 시도한 이메일
     */
    @Transactional
    public String emailStore(EmailDto emailDto,String emailKey){

        memberRepository.findByEmail(emailDto.getEmail()).ifPresentOrElse(
                member -> {
                    if(member.getEmailAuth().equals("DONE") || member.getEmailAuth().equals("JoinDone")){
                        throw new CustomException(ErrorCode.EMAIL_AUTHENTICATION);
                    }
                    member.emailSeveralRequest(emailDto,emailKey);
                },
                ()->memberRepository.save(emailDto.toEntity(emailKey))
        );
        return emailDto.getEmail();

    }

    /**
     * 이메일 인증코드 확인
     * @param memberEmail 이메일,인증코드
     */
    @Transactional
    public String codeCheck(String memberEmail) {

        Member member=memberRepository.findByEmail(memberEmail).orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if(member.getEmailAuth().equals("DONE") ||member.getEmailAuth().equals("JoinDone") ){
            throw new CustomException(ErrorCode.EMAIL_AUTHENTICATION);
        }
        else if(!member.getEmailAuth().equals(member.getEmailAuth()) || member.getEmailAuth() == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_AUTHENTICATION);
        }
        member.updateEmailAuth("DONE");
        return member.getEmailAuth();
    }

    /**
     * 회원가입
     * @param requestDto 회원 정보
     */
    @Transactional
    public void signUp(RequestJoinDto requestDto) {

        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!requestDto.getPassword().equals(requestDto.getPwcheck())){
            throw new CustomException(ErrorCode.PASSWORD_NOT_EQUALS);
        }
        else if(member.getEmailAuth().equals("JoinDone")){
            throw new CustomException(ErrorCode.MEMBER_FOUND);
        }
        else if(!member.getEmailAuth().equals("DONE")){
            throw new CustomException(ErrorCode.EMAIL_YET_AUTHENTICATION);
        }

        member.joinMember(requestDto);
        member.encodePassword(passwordEncoder);

    }

    /**
     * 이메일 인증 확인 여부
     * @param memberEmail 사용자 이메일
     * @return
     */
    @Transactional(readOnly = true)
    public String emailAuth(String memberEmail) {

        String emailKey=memberRepository.findByEmail(memberEmail).get().getEmailAuth();

        if (!emailKey.equals("DONE")){
            throw new CustomException(ErrorCode.EMAIL_YET_AUTHENTICATION);
        }

        return emailKey;
    }
}
