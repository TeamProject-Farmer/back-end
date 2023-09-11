package com.farmer.backend.api.controller.user.orderproduct.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
