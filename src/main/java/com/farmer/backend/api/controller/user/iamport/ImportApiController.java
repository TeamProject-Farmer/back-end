package com.farmer.backend.api.controller.user.iamport;

import com.farmer.backend.api.service.orderproduct.OrderProductService;
import com.farmer.backend.config.ApiDocumentResponse;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/api/main/verifyIamport")
@Slf4j
public class ImportApiController {

    private final IamportClient iamportClient;
    private final OrderProductService orderProductService;

    public ImportApiController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
        this.iamportClient = new IamportClient("7310614134771042", "atck8j8CBuK2HLjMFwTudtzCtLCG3vTKy5CTsFmNesF6lwJiXZnlOyw8ftKcHwhQ8HZ8MJMvsUmOT5eh");
    }

    @GetMapping("/main")
    public String index() {
        return "IamportOrderPage";
    }

    @ResponseBody
    @ApiDocumentResponse
    @Operation(summary = "결제 요청", description = "결제 요청건에 대한 검증을 실행합니다.")
    @PostMapping("/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(@PathVariable(value = "imp_uid") String impUid) throws IamportResponseException, IOException {
        return iamportClient.paymentByImpUid(impUid);
    }
}
