package com.farmer.backend.api.controller.coupon.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class ResponseMembersCouponDto {

    private String name;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

}
