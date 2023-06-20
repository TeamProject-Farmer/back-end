package com.farmer.backend.dto.admin.product.category;

import com.farmer.backend.dto.admin.product.ResponseProductDto;
import com.farmer.backend.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseProductCategoryListDto {

    private Long categoryId;
    private String categoryName;

    public static ResponseProductCategoryListDto productCategoryList(ProductCategory category) {
        return ResponseProductCategoryListDto.builder()
                .categoryId(category.getId())
                .categoryName(category.getName())
                .build();
    }
}
