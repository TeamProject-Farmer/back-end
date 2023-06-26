package com.farmer.backend.dto.admin.settings;

import com.farmer.backend.entity.Coupon;
import com.farmer.backend.entity.CouponPolicy;
import com.farmer.backend.entity.Member;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCouponDto {

    private String couponName;
    private Member member;
    private String serialNumber;
    private String benefits;
    private String discountPolicy;
    private int fixedPrice;
    private int rateAmount;
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime startDateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime endDateTime;
    private char status;

    public Coupon toEntity() {
        return Coupon.builder()
                .member(member)
                .name(couponName)
                .serialNumber(serialNumber)
                .benefits(benefits)
                .discountPolicy(CouponPolicy.valueOf(discountPolicy))
                .fixedPrice(fixedPrice)
                .rateAmount(rateAmount)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .useYn(status)
                .build();
    }

}
