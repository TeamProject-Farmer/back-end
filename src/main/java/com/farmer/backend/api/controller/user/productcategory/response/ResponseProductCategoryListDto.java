package com.farmer.backend.api.controller.user.productcategory.response;

import com.farmer.backend.domain.product.productcategory.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseProductCategoryListDto {

    private Long categoryId;
    private String categoryName;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static ResponseProductCategoryListDto productCategoryList(ProductCategory category) {
        return ResponseProductCategoryListDto.builder()
                .categoryId(category.getId())
                .categoryName(category.getName())
                .build();
    }

    public static ResponseProductCategoryListDto productCategoryDetail(ProductCategory category) {
        return ResponseProductCategoryListDto.builder()
                .categoryId(category.getId())
                .categoryName(category.getName())
                .createdDate(category.getCreatedDate())
                .modifiedDate(category.getModifiedDate())
                .build();
    }
}
