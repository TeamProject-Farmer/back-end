package com.farmer.backend.api.controller.orderproduct.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseOrderProductListDto {

    private Long productId;
    private String productName;
    private String imgUrl;
    private Integer price;

}
