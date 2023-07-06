package com.farmer.backend.api.service.membersCoupon;

import com.farmer.backend.api.controller.coupon.response.ResponseMembersCouponDto;
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
     * @return
     */
    @Transactional(readOnly = true)
    public List<ResponseMembersCouponDto> couponList(String memberEmail) {
        List<ResponseMembersCouponDto> memberCouponList = membersCouponQueryRepositoryImpl.membersCouponList(memberEmail);

        log.info(memberCouponList.toString());
        return memberCouponList;
    }
}
