package com.farmer.backend.api.controller.user.productcategory.request;

import com.farmer.backend.domain.product.productcategory.ProductCategory;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestProductCategoryDto {

    private String categoryName;
    private char useYn;
    private Integer sortOrder;

    public ProductCategory toEntity() {
        return ProductCategory.builder()
                .name(categoryName)
                .useYn(useYn)
                .sortOrder(sortOrder)
                .build();
    }
}
