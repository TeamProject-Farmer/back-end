package com.farmer.backend.api.controller.user.options.request;

import com.farmer.backend.domain.options.Options;
import com.farmer.backend.domain.product.Product;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RequestOptionDto {

    private Long id;
    private String optionName;
    private Product product;
    private Integer optionPrice;

    public Options toEntity() {
        return Options.builder()
                .product(product)
                .optionName(optionName)
                .optionPrice(optionPrice)
                .build();
    }

}
