package com.farmer.backend.dto.admin.orders;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchOrdersCondition {

    private String orderNumber;
    private String memberName;
    private String email;
}
