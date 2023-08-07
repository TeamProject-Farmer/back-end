package com.farmer.backend.api.controller.cart;

import com.farmer.backend.api.controller.cart.request.RequestProductCartDto;
import com.farmer.backend.api.controller.cart.request.RequestCartProductQuantityDto;
import com.farmer.backend.api.controller.cart.response.ResponseCartProductListDto;
import com.farmer.backend.api.controller.cart.response.ResponseCartProductQuantityDto;
import com.farmer.backend.api.controller.cart.response.ResponseTotalPriceAndCountDto;
import com.farmer.backend.api.service.cart.CartService;
import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.login.general.MemberAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member/cart")
@Tag(name = "CartController", description = "장바구니 API")
public class CartController {

    private final CartService cartService;

    /**
     * 상품 상세페이지
     * 장바구니에 상품 추가 기능
     */
    @ApiDocumentResponse
    @Operation(summary = "장바구니 추가", description = "상품 한건을 장바구니에 추가합니다.")
    @PostMapping
    public ResponseEntity addCart(@ModelAttribute RequestProductCartDto productCartDto, @AuthenticationPrincipal MemberAdapter member) {
        cartService.addToCart(productCartDto, member.getUsername());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 장바구니 목록 페이지
     * 장바구니 상품 목록 출력
     */
    @ApiDocumentResponse
    @Operation(summary = "장바구니 목록", description = "장바구니의 상품 리스트를 출력합니다")
    @GetMapping("/cart-list")
    public List<ResponseCartProductListDto> cartList(@AuthenticationPrincipal MemberAdapter member) {
        return cartService.cartList(member.getUsername());
    }

    /**
     * 장바구니 목록 페이지
     * 장바구니 상품 수량 업데이트
     */
    @ApiDocumentResponse
    @Operation(summary = "장바구니 상품 수량 업데이트", description = "장바구니의 상품 수량을 변경합니다.")
    @PostMapping("/change-quantity")
    public ResponseCartProductQuantityDto changeQuantity(@ModelAttribute RequestCartProductQuantityDto cartProductQuantityDto, @AuthenticationPrincipal MemberAdapter member) {
        return cartService.changeQuantityAction(cartProductQuantityDto, member.getUsername());
    }

    /**
     * 장바구니 목록 페이지
     * 장바구니 상품 삭제
     */
    @ApiDocumentResponse
    @Operation(summary = "장바구니 상품 삭제", description = "장바구니의 상품을 삭제합니다.")
    @PostMapping("/remove-product/{cartId}")
    public ResponseEntity removeToCartProduct(@PathVariable Long[] cartId, @AuthenticationPrincipal MemberAdapter member) {
        cartService.removeToCartProduct(cartId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 장바구니 목록 페이지
     * 장바구니 상품 합계 금액 조회
     */
    @ApiDocumentResponse
    @Operation(summary = "장바구니 상품 합계금액 조회", description = "장바구니의 상품들의 합계 금액을 조회합니다.")
    @GetMapping("/total-price/{cartId}")
    public ResponseEntity<ResponseTotalPriceAndCountDto> totalPriceAndCountOfCartProduct(@PathVariable Long[] cartId, @AuthenticationPrincipal MemberAdapter member) {
        ResponseTotalPriceAndCountDto totalPriceAndCount = cartService.totalPriceAndCountOfCartProduct(cartId);
        return ResponseEntity.ok(totalPriceAndCount);
    }
}
