package com.farmer.backend.api.controller.user.orderproduct.response;

import com.farmer.backend.domain.orders.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
public class ResponseOrderProductDetailDto {

    private String serialNumber;
    private String imgUrl;
    private Long orderProductId;
    private Long productId;
    private String productName;
    private Integer count;
    private String optionName;
    private Integer optionPrice;
    private Long price;
    private Long orderPrice;
    private OrderStatus orderStatus;

    public ResponseOrderProductDetailDto(String serialNumber, String imgUrl, Long orderProductId, Long productId, String productName, Integer count, String optionName, Integer optionPrice, Long price, Long orderPrice, OrderStatus orderStatus) {
        this.serialNumber = serialNumber;
        this.imgUrl = imgUrl;
        this.orderProductId = orderProductId;
        this.productId = productId;
        this.productName = productName;
        this.count = count;
        this.optionName = optionName;
        this.optionPrice = optionPrice;
        this.price = price;
        this.orderPrice = price + optionPrice;
        this.orderStatus = orderStatus;
    }
}
