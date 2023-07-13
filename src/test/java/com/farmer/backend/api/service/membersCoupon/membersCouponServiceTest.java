package com.farmer.backend.api.service.membersCoupon;

import com.farmer.backend.domain.coupon.Coupon;
import com.farmer.backend.domain.coupon.CouponPolicy;
import com.farmer.backend.domain.coupon.CouponRepository;
import com.farmer.backend.domain.memberscoupon.MemberCouponRepository;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Slf4j
class membersCouponServiceTest {

    @Autowired
    MemberCouponRepository memberCouponRepository;
    @Autowired
    CouponRepository couponRepository;

    @Test
    @DisplayName("회원 쿠폰 적용(할인 금액 조회)")
    void applyCoupon() {
        Long findCouponId = memberCouponRepository.findById(2L).orElseThrow(() -> new CustomException(ErrorCode.COUPON_NOT_FOUND)).getCoupons().getId();
        Coupon coupon = couponRepository.findById(findCouponId).orElseThrow(() -> new CustomException(ErrorCode.COUPON_NOT_FOUND));
        if (coupon.getDiscountPolicy().equals(CouponPolicy.FIXED)) {
            assertThat(coupon.getFixedPrice()).isNotZero();
        } else {
            assertThat(coupon.getRateAmount()).isNotZero();
        }

    }

}