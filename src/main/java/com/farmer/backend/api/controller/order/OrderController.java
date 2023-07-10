package com.farmer.backend.api.controller.order;

import com.farmer.backend.api.service.order.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member/orders")
@Tag(name = "OrderController", description = "주문 API")
public class OrderController {

    private OrderService orderService;
}
