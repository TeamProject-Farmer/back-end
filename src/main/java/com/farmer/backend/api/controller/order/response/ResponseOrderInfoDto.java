package com.farmer.backend.api.controller.order.response;

import com.farmer.backend.domain.deliveryaddress.DeliveryAddress;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseOrderInfoDto {

    private String username;
    private String zipcode;
    private String address;
    private String addressDetail;



}
