package com.farmer.backend.domain.product.productreviewstar;

import com.farmer.backend.api.controller.user.review.request.RequestReviewStarDto;
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
public class ProductReviewAverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_star_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private double averageStarRating;
    @ColumnDefault("0")
    private Long fiveStar;
    @ColumnDefault("0")
    private Long fourStar;
    @ColumnDefault("0")
    private Long threeStar;
    @ColumnDefault("0")
    private Long twoStar;
    @ColumnDefault("0")
    private Long oneStar;

    public void updateReviewAverage(RequestReviewStarDto requestReviewStarDto){
        this.id=id;
        this.averageStarRating=requestReviewStarDto.getAverageStarRating();
        this.fiveStar=requestReviewStarDto.getFiveStar();
        this.fourStar=requestReviewStarDto.getFourStar();
        this.threeStar=requestReviewStarDto.getThreeStar();
        this.twoStar=requestReviewStarDto.getTwoStar();
        this.oneStar=requestReviewStarDto.getOneStar();
    }
}
