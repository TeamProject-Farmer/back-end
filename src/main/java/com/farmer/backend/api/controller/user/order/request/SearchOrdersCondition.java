package com.farmer.backend.api.controller.user.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchOrdersCondition {

    private String orderNumber;
    private String memberName;
    private String email;
}
