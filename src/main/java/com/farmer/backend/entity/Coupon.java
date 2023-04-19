package com.farmer.backend.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Coupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Coupon_id")
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

    @NotNull
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private CouponPolicy discountPolicy;

    @ColumnDefault("0")
    private int fixedPrice ;

    @ColumnDefault("0")
    private int rateAmount ;

    @NotNull
    @Column(length = 1)
    private char useYn;

}
