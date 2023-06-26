package com.farmer.backend.dto.admin.product.category;

import com.farmer.backend.entity.ProductCategory;
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
