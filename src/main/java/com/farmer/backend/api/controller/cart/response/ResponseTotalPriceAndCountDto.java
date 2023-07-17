package com.farmer.backend.api.controller.cart.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseTotalPriceAndCountDto {

    private Integer count;
    private Integer totalPrice;
}
