package com.farmer.backend.api.controller.coupon.response;

import com.farmer.backend.domain.coupon.Coupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseCouponListDto {

    private Long couponId;
    private String couponName;
    private String serialNumber;
    private String benefits;
    private char useYn;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public static ResponseCouponListDto couponList(Coupon coupon) {
        return ResponseCouponListDto.builder()
                .couponId(coupon.getId())
                .couponName(coupon.getName())
                .serialNumber(coupon.getSerialNumber())
                .benefits(coupon.getBenefits())
                .useYn(coupon.getUseYn())
                .startDateTime(coupon.getCreatedDate())
                .endDateTime(coupon.getEndDateTime())
                .build();
    }

}
