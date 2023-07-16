package com.farmer.backend.api.controller.cart.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RequestProductCartDto {

    private Long productId;
    private String productName;
    private Long price;
    private String option;
    private Integer count;

}
