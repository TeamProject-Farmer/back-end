package com.farmer.backend.api.controller.admin.order.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ResponseOrderDetailDto {

    private String orderNumber;
    private LocalDateTime createdDate;
    private String nickname;
    private String phoneNumber;
    private String address;
    private String email;

}
