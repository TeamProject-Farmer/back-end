package com.farmer.backend.api.controller.orderproduct.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ResponseOrderProductListDto {

    private Long productId;
    private String productName;
    private String imgUrl;
    private Long price;
    private Integer count;
}
