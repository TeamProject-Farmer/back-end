package com.farmer.backend.api.controller.order.response;

import com.farmer.backend.api.controller.order.request.RequestOrderInfoDto;
import com.farmer.backend.domain.delivery.Delivery;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.orders.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ResponseOrderCompleteDto {

    private String name;
    private String phoneNumber;
    private String address;
    private Long paymentPrice;
    private LocalDateTime orderedDate;
    private String orderNumber;

    public static ResponseOrderCompleteDto orderCompleteData(Member findMember, Delivery savedDelivery, RequestOrderInfoDto orderInfo, Orders createdOrder) {
        return ResponseOrderCompleteDto.builder()
                .name(findMember.getNickname())
                .phoneNumber(orderInfo.getPhoneNumber())
                .address(savedDelivery.getAddress())
                .paymentPrice(orderInfo.getOrderTotalPrice())
                .orderedDate(createdOrder.getCreatedDate())
                .orderNumber(createdOrder.getOrderNumber())
                .build();
    }
}
