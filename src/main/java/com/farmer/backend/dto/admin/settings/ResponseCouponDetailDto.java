package com.farmer.backend.dto.admin.settings;

import com.farmer.backend.entity.Coupon;
import com.farmer.backend.entity.CouponPolicy;
import com.farmer.backend.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResponseCouponDetailDto {

    private Long couponId;
    private String accountMember;
    private String serialNumber;
    private String couponName;
    private String benefits;
    private char useYn;
    private CouponPolicy discountPolicy;
    private int fixedPrice;
    private int rateAmount;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public static ResponseCouponDetailDto getCouponDetail(Coupon coupon) {
        return ResponseCouponDetailDto.builder()
                .couponId(coupon.getId())
                .accountMember(coupon.getMember().getNickname())
                .serialNumber(coupon.getSerialNumber())
                .couponName(coupon.getName())
                .benefits(coupon.getBenefits())
                .useYn(coupon.getUseYn())
                .discountPolicy(coupon.getDiscountPolicy())
                .fixedPrice(coupon.getFixedPrice())
                .rateAmount(coupon.getRateAmount())
                .createdDate(coupon.getCreatedDate())
                .modifiedDate(coupon.getModifiedDate())
                .startDateTime(coupon.getStartDateTime())
                .endDateTime(coupon.getEndDateTime())
                .build();
    }
}
