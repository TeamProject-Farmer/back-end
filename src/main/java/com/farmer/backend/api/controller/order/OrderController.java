package com.farmer.backend.api.controller.order;

import com.farmer.backend.api.controller.order.request.RequestOrderInfoDto;
import com.farmer.backend.api.controller.order.response.ResponseOrderInfoDto;
import com.farmer.backend.api.controller.orderproduct.response.ResponseOrderProductListDto;
import com.farmer.backend.api.service.order.OrderService;
import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.domain.delivery.DeliveryMemo;
import com.farmer.backend.login.general.MemberAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member/orders")
@Tag(name = "OrderController", description = "주문 API")
public class OrderController {

    private final OrderService orderService;


    /**
     * 주문/결제 페이지
     * 주문자 최근 배송지 정보 리턴
     * 주문자 정보가 없다면 null, 그게 아니면 배송정보를 response
     */
    @ApiDocumentResponse
    @Operation(summary = "주문자 최근 배송지 정보", description = "해당 회원의 최근 배송지 정보를 출력합니다.")
    @GetMapping("/address")
    public ResponseEntity orderMemberInfo(@AuthenticationPrincipal MemberAdapter member) {
        ResponseOrderInfoDto orderInfoDto = orderService.deliveryAddressForm(member.getUsername());
        return ResponseEntity.ok(orderInfoDto);
    }

    /**
     * 주문/결제 페이지
     * 배송 메모 리스트 조회
     */
    @ApiDocumentResponse
    @Operation(summary = "배송 메모 리스트", description = "배송 메모 정보 리스트를 출력합니다.")
    @GetMapping("/delivery/memo-list")
    public Map<DeliveryMemo, String> deliveryMemoInfo() {
        List<DeliveryMemo> memoKeys = Stream.of(DeliveryMemo.values()).collect(Collectors.toList());
        Map<DeliveryMemo, String> memo = new HashMap<>();
        for (DeliveryMemo memoKey : memoKeys) {
            memo.put(memoKey, memoKey.getName());
        }
        return memo;
    }

    /**
     * 주문/결제 페이지
     * 주문 결제
     */
    @ApiDocumentResponse
    @Operation(summary = "주문 결제 요청", description = "주문 한건을 생성합니다.")
    @PostMapping
    public ResponseEntity order(@AuthenticationPrincipal MemberAdapter member, RequestOrderInfoDto orderInfoDto) {
        log.info("orderInfo={}", orderInfoDto.getOrderNumber().toString());
        return ResponseEntity.ok(orderService.createOrder(orderInfoDto));
    }


}
