package com.farmer.backend.api.controller.review.request;

import com.farmer.backend.domain.product.Product;
import com.farmer.backend.domain.product.productreviewstar.ProductReviewAverage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestReviewStarDto {

    private double averageStarRating;
    private Long fiveStar;
    private Long fourStar;
    private Long threeStar;
    private Long twoStar;
    private Long oneStar;

    @Builder
    public RequestReviewStarDto(double averageStarRating , Long fiveStar , Long fourStar, Long threeStar,
                                Long twoStar, Long oneStar){
        this.averageStarRating=averageStarRating;
        this.fiveStar=fiveStar;
        this.fourStar=fourStar;
        this.threeStar=threeStar;
        this.twoStar=twoStar;
        this.oneStar=oneStar;
    }

    public ProductReviewAverage toEntity(Product product){
        return ProductReviewAverage.builder()
                .product(product)
                .averageStarRating(averageStarRating)
                .fiveStar(fiveStar)
                .fourStar(fourStar)
                .threeStar(threeStar)
                .twoStar(twoStar)
                .oneStar(oneStar)
                .build();
    }
}
