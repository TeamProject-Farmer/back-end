package com.farmer.backend.api.controller.search.response;

import lombok.*;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class ResponseSearchProductDto {

    private String thumbnailImg;
    private String name;
    private Integer price;
    private Integer discountRate;
    private Long reviewCount;
    private Double averageStarRating;

}