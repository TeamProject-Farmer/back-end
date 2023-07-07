package com.farmer.backend.api.controller.coupon;

import com.farmer.backend.api.controller.coupon.response.ResponseCouponListDto;
import com.farmer.backend.api.controller.coupon.response.ResponseMembersCouponDto;
import com.farmer.backend.api.service.membersCoupon.membersCouponService;
import com.farmer.backend.config.ApiDocumentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/member")
@Tag(name = "CouponController", description = " 회원 쿠폰 페이지 API")
public class CouponController {


    private final membersCouponService membersCouponService;

    /**
     * 회원 쿠폰 전체 리스트 조회
     */
    @ApiDocumentResponse
    @Operation(summary = "회원 보유 쿠폰 조회",description = "회원이 보유한 쿠폰 리스트를 출력합니다.")
    @PostMapping("/coupon")
    public List<ResponseMembersCouponDto> couponList(Authentication authentication){

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return membersCouponService.couponList(userDetails.getUsername());
    }

    /**
     * 쿠폰 추가
     */

    /**
     * 쿠폰 사용(적용 후 가격, 쿠폰 삭제(비활성))
     */
}
