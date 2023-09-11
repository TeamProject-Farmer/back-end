package com.farmer.backend.api.controller.user.product.response;

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
    private String imgUrl;
    private Integer discountRate;
    private Integer price;
    private Double averageStarRating;
    private Long reviewCount;
}
