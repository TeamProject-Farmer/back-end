package com.farmer.backend.api.service.membersCoupon;

import com.farmer.backend.api.controller.coupon.response.ResponseMembersCouponDto;
import com.farmer.backend.api.controller.coupon.response.ResponseUseCouponListDto;
import com.farmer.backend.domain.coupon.CouponRepository;
import com.farmer.backend.domain.memberscoupon.MemberCouponRepository;
import com.farmer.backend.domain.memberscoupon.MembersCoupon;
import com.farmer.backend.domain.memberscoupon.MembersCouponQueryRepositoryImpl;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
@Slf4j
class MembersCouponServiceTest {

    @Autowired
    MemberCouponRepository memberCouponRepository;
    @Autowired
    MembersCouponQueryRepositoryImpl membersCouponQueryRepositoryImpl;
    @Autowired
    CouponRepository couponRepository;

    @Test
    @DisplayName("회원 보유 쿠폰 리스트 조회 ")
    void couponList(){

        String memberEmail = "codms7020@naver.com";
        List<ResponseMembersCouponDto> membersCouponDtoList = membersCouponQueryRepositoryImpl.membersCouponList(memberEmail);

        for( ResponseMembersCouponDto membersCouponDto : membersCouponDtoList){
            log.info(membersCouponDto.getName());
        }

    }

    @Test
    @DisplayName("회원 쿠폰 적용 가능 리스트")
    void applyCoupon() {
        String memberEmail = "codms7020@naver.com";
        List<ResponseUseCouponListDto> membersCouponDtoList = membersCouponQueryRepositoryImpl.useCouponList(memberEmail);

        for( ResponseUseCouponListDto useCouponListDto : membersCouponDtoList){

            log.info(useCouponListDto.getCouponId() + ":" + useCouponListDto.getName());
            log.info(useCouponListDto.getCouponPolicy().name());
        }

    }

    @Test
    @DisplayName("쿠폰 삭제")
    void delCoupon(){

        MembersCoupon membersCoupon
                = memberCouponRepository.findById(3L).orElseThrow(()-> new CustomException(ErrorCode.COUPON_NOT_FOUND));

        memberCouponRepository.delete(membersCoupon);


    }

}