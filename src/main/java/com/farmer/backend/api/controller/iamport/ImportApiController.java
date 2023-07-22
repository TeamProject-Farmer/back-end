package com.farmer.backend.api.controller.iamport;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/verifyIamport")
@Slf4j
public class ImportApiController {

    private final IamportClient iamportClient;

    public ImportApiController() {
        this.iamportClient = new IamportClient("6276271433478523", "ofRhg3AJqmyhu2G47G8L599vy1cpZsAT1z9Ii8Dlh28NTLfaeUGl8pab0rlJykeIMdn8VtiERbHm6tzx");
    }

    @PostMapping("/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(@PathVariable(value = "imp_uid") String impUid) throws IamportResponseException, IOException {
        log.info("paymentByImpUid 진입");
        return iamportClient.paymentByImpUid(impUid);
    }
}
