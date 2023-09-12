package com.farmer.backend.domain.coupon;

import com.farmer.backend.domain.member.Member;
import com.farmer.backend.api.controller.user.coupon.request.RequestCouponDetailDto;
import com.farmer.backend.domain.BaseTimeEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Column(length = 20)
    private String serialNumber;

    @NotNull
    @Column(length = 50)
    private String name;

    //쿠폰에 대한 간략한 설명
    @NotNull
    @Column(length = 100)
    private String benefits;

    @NotNull
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private CouponPolicy discountPolicy;

    @ColumnDefault("0")
    private int fixedPrice;

    @ColumnDefault("0")
    private int rateAmount;

    @NotNull
    @Column(length = 1)
    private char useYn;

    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime startDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime endDateTime;

    public void modifiedCoupon(RequestCouponDetailDto couponDetailDto) {
        this.name = couponDetailDto.getCouponName();
        this.serialNumber = couponDetailDto.getSerialNumber();
        this.member = couponDetailDto.getMember();
        this.benefits = couponDetailDto.getBenefits();
        this.discountPolicy = couponDetailDto.getDiscountPolicy();
        this.fixedPrice = couponDetailDto.getFixedPrice();
        this.rateAmount = couponDetailDto.getRateAmount();
        this.useYn = couponDetailDto.getUseYn();
        this.startDateTime = couponDetailDto.getStartDateTime();
        this.endDateTime = couponDetailDto.getEndDateTime();
    }


}
