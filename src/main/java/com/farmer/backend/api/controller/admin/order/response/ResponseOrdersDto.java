package com.farmer.backend.api.controller.admin.order.response;

import com.farmer.backend.domain.orders.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ResponseOrdersDto {

//    private Long id;
//    private Member member;
//    private Delivery delivery;
//    private String orderNumber;
//    private OrderStatus orderStatus;
//    private Long orderPrice;
//    private String payMethod;
//    private Integer totalQuantity;
//    private String comment;
//    private LocalDateTime createdDate;

    private Long id;
    private String memberName;
    private String email;
    private String hp;
    private OrderStatus orderStatus;
    private Long orderPrice;
    private String payMethod;
    private Integer totalQuantity;
    private String comment;
    private LocalDateTime createdDate;

}
