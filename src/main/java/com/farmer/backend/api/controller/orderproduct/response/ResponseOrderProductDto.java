package com.farmer.backend.api.controller.orderproduct.response;

import com.farmer.backend.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseOrderProductDto {

    private Long productId;
    private String productName;
    private String imgUrl;

    public static ResponseOrderProductDto shoppingList(Long productId, String productName, String imgUrl) {
        return ResponseOrderProductDto.builder()
                .productId(productId)
                .productName(productName)
                .imgUrl(imgUrl)
                .build();
    }

}
