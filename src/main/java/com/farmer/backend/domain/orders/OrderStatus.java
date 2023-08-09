package com.farmer.backend.domain.orders;

import lombok.Getter;

@Getter
public enum OrderStatus {
    WAIT("결제대기"), FAIL("주문실패"), DONE("주문완료"), CANCEL("주문취소"), REFUND("환불"), EXCHANGE("교환"), RETURN("반품"),
    ALL("전체 주문처리 상태");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

}
