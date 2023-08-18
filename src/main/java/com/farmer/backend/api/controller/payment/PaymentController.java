package com.farmer.backend.api.controller.payment;

import com.farmer.backend.api.controller.payment.request.RequestPaymentInfoDto;
import com.farmer.backend.api.service.payment.PaymentService;
import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.login.general.MemberAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member/payment")
@Tag(name = "PaymentController", description = "결제정보 API")
public class PaymentController {

    private final PaymentService paymentService;

    @ApiDocumentResponse
    @Operation(summary = "결제 완료", description = "상품 주문 후 결제 완료 시 주문 데이터 생성")
    @PostMapping
    public ResponseEntity completePayment(RequestPaymentInfoDto paymentInfoDto, @AuthenticationPrincipal MemberAdapter member) {
        paymentService.completePayment(paymentInfoDto, member);
        return null;
    }


}
