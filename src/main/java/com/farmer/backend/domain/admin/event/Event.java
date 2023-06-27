package com.farmer.backend.domain.admin.event;

import com.farmer.backend.domain.BaseTimeEntity;
import com.farmer.backend.domain.coupon.Coupon;
import com.farmer.backend.domain.member.Member;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @NotNull
    @Column(columnDefinition = "text")
    private String content;

    @Column(length = 255)
    private String imgLink;



}
