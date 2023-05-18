package com.farmer.backend.dto.admin.product;

import com.farmer.backend.entity.ProductCategory;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseCategoryDto {

    private Long categoryId;
    private String categoryName;

    public static ResponseCategoryDto categoryList(ProductCategory category) {
        return ResponseCategoryDto.builder()
                .categoryId(category.getId())
                .categoryName(category.getName())
                .build();
    }
}
