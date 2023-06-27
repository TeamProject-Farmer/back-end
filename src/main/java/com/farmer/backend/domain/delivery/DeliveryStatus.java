package com.farmer.backend.domain.delivery;

public enum DeliveryStatus {

    BEFORE("배송전"), RUN("배송중"), DONE("배송완료");

    private String name;

    DeliveryStatus(String name) {
        this.name = name;
    }
}
