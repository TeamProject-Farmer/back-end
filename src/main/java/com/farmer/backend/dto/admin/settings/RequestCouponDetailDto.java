package com.farmer.backend.dto.admin.settings;

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
public class RequestCouponDetailDto {

    private Long couponId;
    private Member member;
    private String serialNumber;
    private String couponName;
    private String benefits;
    private char useYn;
    private CouponPolicy discountPolicy;
    private int fixedPrice;
    private int rateAmount;
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime startDateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime endDateTime;
}
