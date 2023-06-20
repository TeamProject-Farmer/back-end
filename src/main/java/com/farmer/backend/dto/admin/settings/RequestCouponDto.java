package com.farmer.backend.dto.admin.settings;

import com.farmer.backend.entity.Coupon;
import com.farmer.backend.entity.CouponPolicy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCouponDto {

    private String couponName;
    private String serialNumber;
    private String benefits;
    private CouponPolicy discountPolicy;
    private int fixedPrice;
    private int rateAmount;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private char status;

    public Coupon toEntity() {
        return Coupon.builder()
                .name(couponName)
                .serialNumber(serialNumber)
                .benefits(benefits)
                .discountPolicy(discountPolicy)
                .fixedPrice(fixedPrice)
                .rateAmount(rateAmount)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .useYn(status)
                .build();
    }

}
