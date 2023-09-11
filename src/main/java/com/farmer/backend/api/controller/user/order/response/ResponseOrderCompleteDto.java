package com.farmer.backend.api.controller.user.order.response;

import com.farmer.backend.api.controller.user.order.request.RequestOrderInfoDto;
import com.farmer.backend.domain.delivery.Delivery;
import com.farmer.backend.domain.deliveryaddress.DeliveryAddress;
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

    public static ResponseOrderCompleteDto orderCompleteData(Orders findOrders, DeliveryAddress memberInfo) {
        return ResponseOrderCompleteDto.builder()
                .name(memberInfo.getName())
                .phoneNumber(memberInfo.getHp())
                .address(memberInfo.getAddress() + " " + memberInfo.getAddressDetail() + " " + memberInfo.getZipcode())
                .paymentPrice(findOrders.getOrderPrice())
                .orderedDate(findOrders.getCreatedDate())
                .orderNumber(findOrders.getOrderNumber())
                .build();
    }
}
