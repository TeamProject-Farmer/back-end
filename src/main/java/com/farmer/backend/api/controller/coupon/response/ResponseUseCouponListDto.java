package com.farmer.backend.api.controller.coupon.response;

import com.farmer.backend.domain.coupon.CouponPolicy;
import lombok.*;

@Getter
@NoArgsConstructor
@Builder
public class ResponseUseCouponListDto {

    private Long couponId;
    private String name;
    private String benefits;
    private CouponPolicy couponPolicy;
    private int fixedPrice;
    private int rateAmount;


    public ResponseUseCouponListDto(Long couponId, String name, String benefits, CouponPolicy couponPolicy, int fixedPrice, int rateAmount){
        this.couponId=couponId;
        this.name=name;
        this.benefits=benefits;
        this.couponPolicy=couponPolicy;
        this.fixedPrice=fixedPrice;
        this.rateAmount=rateAmount;
    }


}
