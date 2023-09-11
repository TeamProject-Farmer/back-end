package com.farmer.backend.api.controller.user.coupon.request;

import com.farmer.backend.domain.coupon.Coupon;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.memberscoupon.MembersCoupon;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestMembersCouponDto {

    private Coupon coupon;
    private Member member;

    public MembersCoupon toEntity(){
        return MembersCoupon.builder()
                .coupons(coupon)
                .member(member)
                .build();
    }
}
