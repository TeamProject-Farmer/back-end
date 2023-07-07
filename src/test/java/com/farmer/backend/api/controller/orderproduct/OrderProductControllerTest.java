package com.farmer.backend.api.controller.orderproduct;

import com.farmer.backend.api.controller.orderproduct.request.RequestOrderProductStatusSearchDto;
import com.farmer.backend.api.controller.orderproduct.response.ResponseOrderProductDetailDto;
import com.farmer.backend.api.controller.orderproduct.response.ResponseOrderProductDto;
import com.farmer.backend.api.service.member.MemberService;
import com.farmer.backend.api.service.orderproduct.OrderProductService;
import com.farmer.backend.config.WithMockCustomUser;
import com.farmer.backend.login.general.LoginService;
import com.farmer.backend.login.general.MemberAdapter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Transactional
@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
class OrderProductControllerTest {

    @Autowired
    OrderProductService orderProductService;
    @Autowired
    OrderProductController orderProductController;
    @Autowired
    LoginService loginService;
    @Autowired
    MemberService memberService;


    @Test
    @DisplayName("마이페이지 구매목록 조회")
    @WithMockCustomUser
    void shoppingList() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        MemberAdapter memberAdapter = (MemberAdapter) loginService.loadUserByUsername(authentication.getPrincipal().toString());
        log.info("member={}", memberAdapter);
        List<ResponseOrderProductDto> responseOrderProductDtos = orderProductController.shoppingList(memberAdapter);
        for (ResponseOrderProductDto responseOrderProductDto : responseOrderProductDtos) {
            log.info("responseOrderProductDto={}", responseOrderProductDto.getProductName());
        }

    }

    @Test
    @DisplayName("주문내역 상품리스트 전체 조회")
    @WithMockCustomUser
    void orderList() {
        RequestOrderProductStatusSearchDto statusSearchDto = new RequestOrderProductStatusSearchDto();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime st = LocalDate.parse("2022-02-03", dateTimeFormatter).atStartOfDay();
        LocalDateTime ed = LocalDate.parse("2023-02-03", dateTimeFormatter).atStartOfDay();

//        LocalDateTime st = LocalDateTime.parse("2022-02-03");
//        LocalDateTime ed = LocalDateTime.parse("2023-05-03");

        statusSearchDto.setStartDate(st);
        statusSearchDto.setEndDate(ed);
        List<ResponseOrderProductDetailDto> responseOrderProductDetailDtos = orderProductService.orderList(statusSearchDto, "2020112112@dgu.ac.kr");
        for (ResponseOrderProductDetailDto responseOrderProductDetailDto : responseOrderProductDetailDtos) {
            log.info("responseOrderProductDetailDto={}", responseOrderProductDetailDto.getProductName());
        }

        log.info("st={}", st);
        log.info("ed={}", ed);
    }

}