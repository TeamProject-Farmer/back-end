package com.farmer.backend.api.service.member;

import com.farmer.backend.api.controller.user.member.request.RequestMemberDto;
import com.farmer.backend.api.controller.user.member.request.RequestMemberProfileDto;
import com.farmer.backend.api.controller.user.member.response.ResponseMemberDto;
import com.farmer.backend.api.controller.user.member.request.SearchMemberCondition;
import com.farmer.backend.api.controller.user.join.EmailDto;
import com.farmer.backend.api.controller.user.join.RequestJoinDto;
import com.farmer.backend.api.controller.user.login.ResponseOAuthUserInfoDto;
import com.farmer.backend.api.controller.user.member.response.ResponseMemberInfoDto;
import com.farmer.backend.api.controller.user.member.response.ResponseMemberListDto;
import com.farmer.backend.api.controller.user.member.response.ResponseMemberPoint;
import com.farmer.backend.api.service.membersCoupon.MembersCouponService;
import com.farmer.backend.domain.member.*;
import com.farmer.backend.domain.memberscoupon.MemberCouponRepository;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.login.oauth.userInfo.GoogleSocialLogin;
import com.farmer.backend.login.oauth.userInfo.KakaoSocialLogin;
import com.farmer.backend.login.oauth.userInfo.NaverSocialLogin;
import com.farmer.backend.api.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MailService mailService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberQueryRepository memberQueryRepositoryImpl;
    private final MemberCouponRepository memberCouponRepository;
    private final GoogleSocialLogin googleSocialLogin;
    private final KakaoSocialLogin kakaoSocialLogin;
    private final NaverSocialLogin naverSocialLogin;
    private final MembersCouponService membersCouponService;


    /**
     * 이메일 인증 시도한 이메일 저장
     * @param emailDto 이메일 데이터
     * @return 인증 시도한 이메일
     */
    @Transactional
    public String emailStore(EmailDto emailDto){

        memberRepository.findByEmail(emailDto.getEmail()).ifPresentOrElse(
                member -> {
                    if(member.getEmailAuth().equals("DONE") || member.getEmailAuth().equals("JoinDone")){
                        throw new CustomException(ErrorCode.EMAIL_AUTHENTICATION);
                    }
                    String emailKey = mailService.sendAuthMail(member.getEmail());
                    member.emailSeveralRequest(emailDto,emailKey);
                },
                ()->{
                    String emailKey = mailService.sendAuthMail(emailDto.getEmail());
                    memberRepository.save(emailDto.toEntity(emailKey));
                }
        );
        return emailDto.getEmail();

    }

    /**
     * 이메일 인증코드 확인
     * @param memberEmail 이메일,인증코드
     */
    @Transactional
    public void codeCheck(String memberEmail) {

        Member member=memberRepository.findByEmail(memberEmail).orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if(member.getEmailAuth().equals("DONE") ||member.getEmailAuth().equals("JoinDone") ){
            throw new CustomException(ErrorCode.EMAIL_AUTHENTICATION);
        }
        else if(!member.getEmailAuth().equals(member.getEmailAuth()) || member.getEmailAuth() == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_AUTHENTICATION);
        }
        member.updateEmailAuth("DONE");
    }

    /**
     * 회원가입
     * @param requestDto 회원 정보
     */
    @Transactional
    public void signUp(RequestJoinDto requestDto) {

        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if(member.getEmailAuth().equals("JoinDone")){
            throw new CustomException(ErrorCode.MEMBER_FOUND);
        }
        else if(!member.getEmailAuth().equals("DONE")){
            throw new CustomException(ErrorCode.EMAIL_YET_AUTHENTICATION);
        }

        if(memberRepository.findByNickname(requestDto.getNickname()).isPresent()){
            throw new CustomException(ErrorCode.NICKNAME_FOUND);
        }
        member.joinMember(requestDto);
        membersCouponService.joinCoupon(member);
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

        if (!emailKey.equals("DONE")|| !emailKey.equals("JoinDone")){
            throw new CustomException(ErrorCode.EMAIL_YET_AUTHENTICATION);
        }

        return emailKey;
    }

    /**
     * 소셜 로그인
     * @param socialType (kakao, google, naver) 소셜 로그인 타입
     * @param code 인가코드
     */
    public ResponseOAuthUserInfoDto socialUserInfo(String socialType, String code) {

        if (socialType.equals("google")) {
            return googleSocialLogin.getUserInfo(code);
        }
        else if (socialType.equals("kakao")){
            return kakaoSocialLogin.getUserInfo(code);
        }

        return naverSocialLogin.getUserInfo(code);

    }

    /**
     * 회원 프로필 수정
     *
     * @param memberEmail 회원 이메일
     * @param requestMemberProfileDto 프로필 수정 데이터
     */
    @Transactional
    public String profileUpdate(String memberEmail, RequestMemberProfileDto requestMemberProfileDto) {

        Member member=memberRepository.findByEmail(memberEmail).orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Long memberCoupon = memberCouponRepository.countByMemberId(member.getId());
        String nickname = requestMemberProfileDto.getNickname();
        Optional<String> socialId = Optional.ofNullable(member.getSocialId());

        if(!member.getNickname().equals(nickname) && memberRepository.findByNickname(nickname).isPresent()){
            throw new CustomException(ErrorCode.NICKNAME_FOUND);
        }
        else if(!socialId.equals(null)){
            member.updateProfile(member.getPassword(),requestMemberProfileDto.getNickname());
        }
        else{
            member.updateProfile(requestMemberProfileDto.getPassword(),requestMemberProfileDto.getNickname());
            member.encodePassword(passwordEncoder);
        }

        return "수정 완료";
    }

    /**
     * 회원 적립금
     * @param memberEmail
     * @return
     */
    @Transactional(readOnly = true)
    public ResponseMemberPoint getPoint(String memberEmail) {
        Long point = memberRepository.findByEmail(memberEmail).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)).getPoint();
        return new ResponseMemberPoint(point);
    }

    /**
     * =========================================================================================================
     * Admin logic
     */

    /**
     * 전체 회원 리스트 (admin)
     * @param member 관리자
     * @param sortOrderCond 정렬순서
     * @param searchMemberCond 검색정보
     */
    @Transactional(readOnly = true)
    public List<ResponseMemberListDto> memberList(Member member, String sortOrderCond, SearchMemberCondition searchMemberCond) {

        if(!member.getRole().equals(UserRole.ADMIN)){
            throw new CustomException(ErrorCode.ADMIN_ACCESS);
        }

        return memberQueryRepositoryImpl.memberList(sortOrderCond,searchMemberCond);
    }

    /**
     * 특정 회원 정보 조회 (admin)
     * @param member 관리자
     * @param memberId 특정 회원 ID
     */
    @Transactional
    public ResponseMemberInfoDto memberInfo(Member member, Long memberId) {

        if(!member.getRole().equals(UserRole.ADMIN)){
            throw new CustomException(ErrorCode.ADMIN_ACCESS);
        }

        return memberQueryRepositoryImpl.memberInfo(memberId);
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
     * 회원 계정 상태 변경 - 삭제 (admin)
     * @param member 관리자
     * @param memberId 회원 일련번호 배열
     * @return
     */
    @Transactional
    public ResponseEntity<String> deleteMember(Member member, Long memberId) {

        if(!member.getRole().equals(UserRole.ADMIN)){
            throw new CustomException(ErrorCode.ADMIN_ACCESS);
        }

        memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        memberQueryRepositoryImpl.deleteMember(memberId);

        return new ResponseEntity<>("OK",HttpStatus.OK);
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


}
