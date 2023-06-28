package com.farmer.backend.domain.coupon;

public enum CouponPolicy {

    RATE("할인정책 정률"),FIXED("정액");

    private String name;

    CouponPolicy(String name) {
        this.name = name;
    }

}
