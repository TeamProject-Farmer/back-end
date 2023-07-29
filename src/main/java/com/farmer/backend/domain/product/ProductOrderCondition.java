package com.farmer.backend.domain.product;

import lombok.Getter;

@Getter
public enum ProductOrderCondition {

    NEWS("신상품순"), REVIEW("리뷰많은순"), LOW("낮은가격순"), HIGH("높은가격순");

    private String orderConditionName;

    ProductOrderCondition(String orderConditionName) {
        this.orderConditionName = orderConditionName;
    }
}
