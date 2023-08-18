package com.farmer.backend.api.controller.payment.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestPaymentInfoDto {

    private Long orderId;
    private Long totalPrice;
    private int discountPrice;
    private int deliveryPrice;
    private int usePointPrice;
    private int addPoint;
    private Long finalAmount;

}
