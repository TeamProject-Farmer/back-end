package com.farmer.backend.api.controller.user.order.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrderProductDto {

    private Long productId;
    private Long optionId;
    private Integer count;
    private Long orderPrice;
}
