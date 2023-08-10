package com.farmer.backend.api.controller.order.request;

import com.farmer.backend.domain.delivery.Delivery;
import com.farmer.backend.domain.delivery.DeliveryMemo;
import com.farmer.backend.domain.delivery.DeliveryStatus;
import com.farmer.backend.domain.deliveryaddress.AddressStatus;
import com.farmer.backend.domain.deliveryaddress.DeliveryAddress;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.orders.OrderStatus;
import com.farmer.backend.domain.orders.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RequestOrderInfoDto {

    //주문상품
    private Long productId;
    private Long optionId;
    private Integer count;

    //배송지
    private String username;
    private String address;
    private String zipcode;
    private String addressDetail;
    private String phoneNumber;
    private DeliveryMemo memo;
    private String selfMemo;
    private String defaultAddr;

    //주문결제
    private String orderNumber;
    private Long orderTotalPrice;
    private Integer totalQuantity;
    private Long point;
    private String payMethod;

    public Delivery toEntityDelivery() {
        return Delivery.builder()
                .address(address + " " + addressDetail + " " + zipcode)
                .deliveryStatus(DeliveryStatus.BEFORE)
                .memo(memo)
                .selfMemo(selfMemo)
                .build();
    }

    public DeliveryAddress toEntityDeliveryAddress(Member member) {
        return DeliveryAddress.builder()
                .member(member)
                .name(member.getNickname())
                .address(address)
                .addressDetail(addressDetail)
                .zipcode(zipcode)
                .hp(phoneNumber)
                .status(AddressStatus.NORMAL)
                .build();
    }

    public Orders toEntity(Member member, Delivery delivery) {
        return Orders.builder()
                .comment(memo.getName())
                .orderPrice(orderTotalPrice)
                .orderStatus(OrderStatus.DONE)
                .payMethod(payMethod)
                .totalQuantity(totalQuantity)
                .member(member)
                .delivery(delivery)
                .orderNumber(orderNumber)
                .build();
    }
}
