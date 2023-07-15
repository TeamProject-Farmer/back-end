package com.farmer.backend.api.controller.cart;

import com.farmer.backend.api.controller.cart.request.RequestProductCartDto;
import com.farmer.backend.api.controller.orderproduct.request.RequestOrderProductList;
import com.farmer.backend.api.controller.product.response.ResponseProductDtoList;
import com.farmer.backend.api.service.cart.CartService;
import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.domain.product.ProductOrderCondition;
import com.farmer.backend.login.general.MemberAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member/cart")
@Tag(name = "CartController", description = "장바구니 API")
public class CartController {

    private final CartService cartService;

    @ApiDocumentResponse
    @Operation(summary = "장바구니 추가", description = "상품 한건을 장바구니에 추가합니다.")
    @PostMapping
    public ResponseEntity addCart(@ModelAttribute RequestProductCartDto productCartDto, @AuthenticationPrincipal MemberAdapter member) {
        cartService.addToCart(productCartDto, member);
        return new ResponseEntity(HttpStatus.OK);
    }
}
