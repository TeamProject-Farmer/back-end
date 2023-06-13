package com.farmer.backend.dto.admin.orders;

import com.farmer.backend.entity.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;
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
