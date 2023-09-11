package com.farmer.backend.api.controller.user.cart.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ResponseCartProductListDto {

    private Long cartId;
    private Long productId;
    private String imgUrl;
    private String productName;
    private Long optionId;
    private String optionName;
    private Integer optionPrice;
    private Integer count;
    private Integer productPrice;
    private Integer totalPrice;

}
