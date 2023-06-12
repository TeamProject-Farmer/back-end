package com.farmer.backend.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
    WAIT("결제대기"), FAIL("주문실패"), DONE("주문완료"), CANCEL("주문취소"), REFUND("환불"), EXCHANGE("교환"), RETURN("반품"),
    ORDER("주문");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

}
