package com.farmer.backend.api.service.membersCoupon;

import com.farmer.backend.api.controller.coupon.response.ResponseMembersCouponDto;
import com.farmer.backend.domain.coupon.Coupon;
import com.farmer.backend.domain.coupon.CouponPolicy;
import com.farmer.backend.domain.coupon.CouponRepository;
import com.farmer.backend.domain.memberscoupon.MemberCouponRepository;
import com.farmer.backend.domain.memberscoupon.MembersCouponQueryRepositoryImpl;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class membersCouponService {

    private final MembersCouponQueryRepositoryImpl membersCouponQueryRepositoryImpl;
    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;

    /**
     * 회원 보유 쿠폰 리스트 조회
     * @return
     */
    @Transactional(readOnly = true)
    public List<ResponseMembersCouponDto> couponList(String memberEmail) {
        List<ResponseMembersCouponDto> memberCouponList = membersCouponQueryRepositoryImpl.membersCouponList(memberEmail);

        log.info(memberCouponList.toString());
        return memberCouponList;
    }

    @Transactional
    public int applyCoupon(String memberEmail, Long couponId) {
        Long findCouponId = memberCouponRepository.findById(couponId).orElseThrow(() -> new CustomException(ErrorCode.COUPON_NOT_FOUND)).getCoupons().getId();
        Coupon coupon = couponRepository.findById(findCouponId).orElseThrow(() -> new CustomException(ErrorCode.COUPON_NOT_FOUND));
        if (coupon.getDiscountPolicy().equals(CouponPolicy.FIXED)) {
            return coupon.getFixedPrice();
        }
        return coupon.getRateAmount();
    }
}
