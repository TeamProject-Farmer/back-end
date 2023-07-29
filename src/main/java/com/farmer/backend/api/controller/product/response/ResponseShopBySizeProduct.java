package com.farmer.backend.api.controller.product.response;

import com.farmer.backend.domain.product.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseShopBySizeProduct {

    private String imgLink;
    private ProductSize size;
    private Long productCategoryId;
}
