package com.farmer.backend.api.controller.user.orderproduct.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestOrderProductList {

    private Long productId;
    private Long optionId;
    private Integer count;
    private Long orderPrice;

}
