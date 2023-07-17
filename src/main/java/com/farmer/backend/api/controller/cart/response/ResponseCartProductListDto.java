package com.farmer.backend.api.controller.cart.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseCartProductListDto {

    private Long productId;
    private String imgUrl;
    private String productName;
    private String optionName;
    private Integer count;
    private Integer productPrice;
    private Integer totalPrice;

}
