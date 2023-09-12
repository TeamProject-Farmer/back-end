package com.farmer.backend.api.controller.admin.order.response;

import com.farmer.backend.api.controller.admin.orderproduct.response.ResponseOrderProductDto;
import com.farmer.backend.domain.orders.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResponseOrderListDto {

    private Long orderId;
    private LocalDateTime orderDate;
    private String orderNumber;
    private String orderName;
    private List<ResponseOrderProductDto> orderProduct;
    private Long orderPrice;
    private Integer totalQuantity;
    private String orderStatus;
    private String payMethod;

    public ResponseOrderListDto(Long orderId, LocalDateTime orderDate, String orderNumber, String orderName, List<ResponseOrderProductDto> orderProduct, Long orderPrice, Integer totalQuantity, OrderStatus orderStatus, String payMethod) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderNumber = orderNumber;
        this.orderName = orderName;
        this.orderProduct = orderProduct;
        this.orderPrice = orderPrice;
        this.totalQuantity = totalQuantity;
        this.orderStatus = orderStatus.getName();
        this.payMethod = payMethod;
    }
}
