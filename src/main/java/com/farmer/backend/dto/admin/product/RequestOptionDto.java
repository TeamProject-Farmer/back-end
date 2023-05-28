package com.farmer.backend.dto.admin.product;

import com.farmer.backend.entity.Options;
import com.farmer.backend.entity.Product;
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
