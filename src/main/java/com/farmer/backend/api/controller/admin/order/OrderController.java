package com.farmer.backend.api.controller.admin.order;

import com.farmer.backend.api.controller.admin.order.response.ResponseOrderDetailDto;
import com.farmer.backend.api.controller.admin.order.response.ResponseOrderListDto;
import com.farmer.backend.api.controller.user.order.request.SearchOrdersCondition;
import com.farmer.backend.api.controller.user.order.response.ResponseOrderInfoDto;
import com.farmer.backend.api.controller.user.order.response.ResponseOrdersDto;
import com.farmer.backend.api.service.order.OrderService;
import com.farmer.backend.config.ApiDocumentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "admin.Controller")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/orders")
@Tag(name = "OrderAdminController", description = "백오피스 주문 API")
public class OrderController {

    private final OrderService orderService;

    @ApiDocumentResponse
    @Operation(summary = "주문 전체조회", description = "관리자 페이지에서 주문 정보를 전체 조회합니다.")
    @GetMapping
    public List<ResponseOrderListDto> orderList(SearchOrdersCondition searchOrdersCondition) {
        return orderService.orderList(searchOrdersCondition);
    }

    @ApiDocumentResponse
    @Operation(summary = "주문상세 정보조회", description = "관리자 페이지에서 주문 상세정보를 조회합니다.")
    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseOrderDetailDto> orderDetail(@PathVariable Long orderId) {
        ResponseOrderDetailDto orderDetail = orderService.orderDetail(orderId);
        return ResponseEntity.ok(orderDetail);
    }

}
