package com.farmer.backend.api.controller.user.orderproduct;

import com.farmer.backend.api.controller.user.orderproduct.request.RequestOrderProductStatusSearchDto;
import com.farmer.backend.api.controller.user.orderproduct.response.ResponseOrderProductDetailDto;
import com.farmer.backend.api.controller.user.orderproduct.response.ResponseOrderProductDto;
import com.farmer.backend.api.service.orderproduct.OrderProductService;
import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.login.general.MemberAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member/order-product")
@Tag(name = "OrderProductController", description = "주문상품 API")
public class OrderProductController {

    private final OrderProductService orderProductService;

    /**
     * 마이페이지 구매목록 기능
     * @param member 세션 회원정보
     * @return List<ResponseOrderProductDto>
     */

    @ApiDocumentResponse
    @Operation(summary = "마이페이지 구매목록 조회", description = "현재 로그인 한 사용자의 구매 목록 리스트를 출력합니다.")
    @GetMapping
    public List<ResponseOrderProductDto> shoppingList(@AuthenticationPrincipal MemberAdapter member) {
        return orderProductService.shoppingList(member.getUsername());
    }

    /**
     * 마이페이지 구매목록 > 주문내역 조회
     * @param statusSearchDto 날짜, 검색조건 정보
     * @param member 세션 회원 정보
     * @return List<ResponseOrderProductDetailDto>
     */
    @ApiDocumentResponse
    @Operation(summary = "주문내역 전체 조회", description = "현재 로그인 한 사용자의 전체 주문내역을 조회합니다.")
    @PostMapping("/order-list")
    public ResponseEntity<List<ResponseOrderProductDetailDto>> orderList(@AuthenticationPrincipal MemberAdapter member,
                                                         @ModelAttribute RequestOrderProductStatusSearchDto statusSearchDto) {
        if (statusSearchDto.getEndDate() == null) {
            return new ResponseEntity(ErrorCode.END_DATE_IS_NULL.getHttpStatus());
        }
        return ResponseEntity.ok(orderProductService.orderList(statusSearchDto, member.getUsername()));
    }
}
