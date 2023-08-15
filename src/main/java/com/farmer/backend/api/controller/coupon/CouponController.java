package com.farmer.backend.api.controller.coupon;

import com.farmer.backend.api.controller.coupon.response.ResponseMembersCouponDto;
import com.farmer.backend.api.controller.coupon.response.ResponseUseCouponListDto;
import com.farmer.backend.api.service.membersCoupon.MembersCouponService;
import com.farmer.backend.config.ApiDocumentResponse;

import com.farmer.backend.login.general.MemberAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/member")
@Tag(name = "CouponController", description = " 회원 쿠폰 페이지 API")
public class CouponController {


    private final MembersCouponService membersCouponService;

    /**
     * 회원 쿠폰 전체 리스트 조회
     */
    @ApiDocumentResponse
    @Operation(summary = "회원 보유 쿠폰 조회",description = "회원이 보유한 쿠폰 리스트를 출력합니다.")
    @GetMapping("/coupon")
    public List<ResponseMembersCouponDto> couponList(@AuthenticationPrincipal MemberAdapter memberAdapter){

        String memberEmail = memberAdapter.getMember().getEmail();
        return membersCouponService.couponList(memberEmail);

    }

    /**
     * 쿠폰 사용
     */
    @ApiDocumentResponse
    @Operation(summary = "상품에 대한 쿠폰 적용",description = "상품에 회원이 보유한 쿠폰을 적용합니다.")
    @GetMapping("/coupon/use")
    public List<ResponseUseCouponListDto> useCouponList(@AuthenticationPrincipal MemberAdapter memberAdapter){

        String memberEmail = memberAdapter.getMember().getEmail();
        return membersCouponService.applyCoupon(memberEmail);


    }

    /**
     * 쿠폰 삭제
     */
    @ApiDocumentResponse
    @Operation(summary = "사용 쿠폰 삭제", description = "상품 구매에 사용한 쿠폰을 삭제합니다.")
    @PostMapping("/coupon/del")
    public ResponseEntity<String> delCoupon(@RequestParam Long memberCouponId){

        return membersCouponService.delCoupon(memberCouponId);

    }
}
