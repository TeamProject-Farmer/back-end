package com.farmer.backend.api.controller.user.order.request;

import com.farmer.backend.domain.delivery.Delivery;
import com.farmer.backend.domain.delivery.DeliveryMemo;
import com.farmer.backend.domain.delivery.DeliveryStatus;
import com.farmer.backend.domain.deliveryaddress.AddressStatus;
import com.farmer.backend.domain.deliveryaddress.DeliveryAddress;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.options.Options;
import com.farmer.backend.domain.orderproduct.OrderProduct;
import com.farmer.backend.domain.orders.OrderStatus;
import com.farmer.backend.domain.orders.Orders;
import com.farmer.backend.domain.product.Product;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestOrderInfoDto {

    //주문상품
    private List<RequestOrderProductDto> orderProduct;

    //배송지
    private String username;
    private String address;
    private String zipcode;
    private String addressDetail;
    private String phoneNumber;
    private DeliveryMemo memo;
    private String selfMemo;
    private boolean defaultAddr;

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

    public OrderProduct toEntityOrderProduct(Product product, Options options, Integer count, Long orderPrice, Orders createdOrder) {
        return OrderProduct.builder()
                .product(product)
                .options(options)
                .count(count)
                .orderPrice(orderPrice)
                .orders(createdOrder)
                .build();
    }

    public Orders toEntity(Member member, Delivery delivery, int totalCount) {
        return Orders.builder()
                .comment(memo.getName())
                .orderPrice(orderTotalPrice)
                .orderStatus(OrderStatus.DONE)
                .payMethod(payMethod)
                .totalQuantity(totalCount)
                .member(member)
                .delivery(delivery)
                .orderNumber(orderNumber)
                .build();
    }

}
