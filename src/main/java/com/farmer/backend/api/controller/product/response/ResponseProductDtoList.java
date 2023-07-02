package com.farmer.backend.api.controller.product.response;

import com.farmer.backend.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseProductDtoList {

    private Long productId;
    private String productName;
    private Integer discountRate;
    private Integer price;
    private Double averageStarRating;
    private Long reviewCount;

    public static ResponseProductDtoList productList(Product product) {
        return ResponseProductDtoList.builder()
                .productId(product.getId())
                .productName(product.getName())
                .discountRate(product.getDiscountRate())
                .price(product.getPrice())
                .averageStarRating(product.getAverageStarRating())
                .reviewCount(product.getReviewCount())
                .build();
    }
}
