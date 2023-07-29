package com.farmer.backend.api.controller.productcategory.response;

import com.farmer.backend.domain.product.productcategory.ProductCategory;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseCategoryDto {

    private Long categoryId;
    private String categoryName;
    private String imgUrl;

    public static ResponseCategoryDto categoryList(ProductCategory category) {
        return ResponseCategoryDto.builder()
                .categoryId(category.getId())
                .categoryName(category.getName())
                .imgUrl(category.getImgUrl())
                .build();
    }
}
