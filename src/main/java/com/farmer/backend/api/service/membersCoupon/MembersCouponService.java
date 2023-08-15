package com.farmer.backend.api.service.membersCoupon;

import com.farmer.backend.api.controller.coupon.request.RequestMembersCouponDto;
import com.farmer.backend.api.controller.coupon.response.ResponseMembersCouponDto;
import com.farmer.backend.api.controller.coupon.response.ResponseUseCouponListDto;
import com.farmer.backend.domain.coupon.Coupon;
import com.farmer.backend.domain.coupon.CouponRepository;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.memberscoupon.MemberCouponRepository;
import com.farmer.backend.domain.memberscoupon.MembersCoupon;
import com.farmer.backend.domain.memberscoupon.MembersCouponQueryRepositoryImpl;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MembersCouponService {

    private final MembersCouponQueryRepositoryImpl membersCouponQueryRepositoryImpl;
    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;


    /**
     * 회원 보유 쿠폰 리스트 조회
     */
    @Transactional(readOnly = true)
    public List<ResponseMembersCouponDto> couponList(String memberEmail) {
        return membersCouponQueryRepositoryImpl.membersCouponList(memberEmail);

    }

    /**
     * 쿠폰 적용
     * @param memberEmail 회원 이메일
     */
    @Transactional
    public List<ResponseUseCouponListDto> applyCoupon(String memberEmail) {
        return membersCouponQueryRepositoryImpl.useCouponList(memberEmail);
    }

    /**
     * 쿠폰 삭제
     * @param memberCouponId 쿠폰 ID
     */
    @Transactional
    public ResponseEntity<String> delCoupon(Long memberCouponId) {

        MembersCoupon membersCoupon = memberCouponRepository.findById(memberCouponId).orElseThrow(()-> new CustomException(ErrorCode.COUPON_NOT_FOUND));
        memberCouponRepository.delete(membersCoupon);

        return new ResponseEntity<>("OK",HttpStatus.OK);
    }

    /**
     * 첫 회원가입 회원 쿠폰 생성
     * @param member
     */
    @Transactional
    public void joinCoupon(Member member) {

        Coupon coupon = couponRepository.findById(3L).orElseThrow(()->new CustomException(ErrorCode.COUPON_NOT_FOUND));
        RequestMembersCouponDto requestMembersCouponDto = new RequestMembersCouponDto(coupon,member);

        MembersCoupon membersCoupon = requestMembersCouponDto.toEntity();
        memberCouponRepository.save(membersCoupon);

    }
}
