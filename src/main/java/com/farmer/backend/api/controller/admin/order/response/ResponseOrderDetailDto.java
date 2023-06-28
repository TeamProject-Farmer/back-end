package com.farmer.backend.api.controller.admin.order.response;

import com.farmer.backend.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class ResponseOrderDetailDto {

    private Product product;
    private Integer count;
    private Long optionId;
    private String optionName;
    private Integer optionPrice;

}
