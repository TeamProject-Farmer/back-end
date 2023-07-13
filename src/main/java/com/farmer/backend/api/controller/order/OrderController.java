package com.farmer.backend.api.controller.order;

import com.farmer.backend.api.controller.order.response.ResponseOrderInfoDto;
import com.farmer.backend.api.service.order.OrderService;
import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.domain.deliveryaddress.DeliveryAddress;
import com.farmer.backend.login.general.MemberAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member/orders")
@Tag(name = "OrderController", description = "주문 API")
public class OrderController {

    private final OrderService orderService;


    /**
     * 주문/결제 페이지
     * 주문자 배송지 정보 리턴
     * 주문자 정보가 없다면 null, 그게 아니면 배송정보를 response
     */
    @ApiDocumentResponse
    @Operation(summary = "주문자 정보 페이지", description = "해당 회원의 주문자 정보를 출력합니다.")
    @GetMapping("/order-form")
    public ResponseEntity orderMemberInfo(@AuthenticationPrincipal MemberAdapter member) {
        ResponseOrderInfoDto orderInfoDto = orderService.orderForm(member.getUsername());
        return ResponseEntity.ok(orderInfoDto);
    }

}
