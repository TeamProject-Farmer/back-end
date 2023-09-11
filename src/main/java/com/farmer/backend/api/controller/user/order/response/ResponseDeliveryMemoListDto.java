package com.farmer.backend.api.controller.user.order.response;

import com.farmer.backend.domain.delivery.DeliveryMemo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDeliveryMemoListDto {

    private String type;
    private String text;

    public static ResponseDeliveryMemoListDto memoList(DeliveryMemo deliveryMemo) {
        return ResponseDeliveryMemoListDto.builder()
                .type(String.valueOf(deliveryMemo))
                .text(String.valueOf(deliveryMemo.getName()))
                .build();
    }
}
