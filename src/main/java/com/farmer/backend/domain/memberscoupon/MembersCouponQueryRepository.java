package com.farmer.backend.domain.memberscoupon;

import com.farmer.backend.api.controller.coupon.response.ResponseMembersCouponDto;
import com.farmer.backend.api.controller.coupon.response.ResponseUseCouponListDto;

import java.util.List;

public interface MembersCouponQueryRepository {
    List<ResponseMembersCouponDto> membersCouponList(String memberEmail);

    List<ResponseUseCouponListDto> useCouponList(String memberEmail);

}
