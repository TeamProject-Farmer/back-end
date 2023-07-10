package com.farmer.backend.domain.product.productreview;

import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.product.Product;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ReviewStarDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_star_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ColumnDefault("0")
    private int fiveStar;
    @ColumnDefault("0")
    private int fourStar;
    @ColumnDefault("0")
    private int threeStar;
    @ColumnDefault("0")
    private int twoStar;
    @ColumnDefault("0")
    private int oneStar;

}
