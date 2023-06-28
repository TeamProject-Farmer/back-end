package com.farmer.backend.domain.deliveryaddress;

public enum AddressStatus {

    NORMAL("기본배송지"), ADD("추가배송지");

    private String name;

    AddressStatus(String name) {
        this.name = name;
    }
}
