package com.farmer.backend.api.controller.orderproduct.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestOrderProductList {

    private Long productId;
    private String productName;
    private Long price;
    private String imgUrl;
    private Integer count;
}
