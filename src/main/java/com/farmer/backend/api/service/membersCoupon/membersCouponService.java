package com.farmer.backend.api.service.membersCoupon;

import com.farmer.backend.api.controller.coupon.response.ResponseMembersCouponDto;
import com.farmer.backend.api.controller.coupon.response.ResponseUseCouponListDto;
import com.farmer.backend.domain.coupon.CouponRepository;
import com.farmer.backend.domain.memberscoupon.MemberCouponRepository;
import com.farmer.backend.domain.memberscoupon.MembersCouponQueryRepositoryImpl;
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


}
