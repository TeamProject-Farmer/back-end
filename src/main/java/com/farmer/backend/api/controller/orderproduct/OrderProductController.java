package com.farmer.backend.api.controller.orderproduct;

import com.farmer.backend.api.controller.orderproduct.response.ResponseOrderProductDto;
import com.farmer.backend.api.service.orderproduct.OrderProductService;
import com.farmer.backend.config.ApiDocumentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/order-product")
@Tag(name = "OrderProductController", description = "주문상품 API")
public class OrderProductController {

    private final OrderProductService orderProductService;

    /**
     * 마이페이지 구매목록 기능
     * @param authentication
     * @return
     */
    @ApiDocumentResponse
    @Operation(summary = "구매목록 조회", description = "현재 로그인 한 사용자의 구매 목록 리스트를 출력합니다.")
    @GetMapping
    public List<ResponseOrderProductDto> shoppingList(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return orderProductService.shoppingList(userDetails.getUsername());
    }
}
