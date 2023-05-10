package com.farmer.backend.entity;

public enum OrderStatus {
    WAIT("결제대기"), FAIL("주문실패"), DONE("주문완료"), CANCEL("주문취소"), REFUND("환불");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

}
