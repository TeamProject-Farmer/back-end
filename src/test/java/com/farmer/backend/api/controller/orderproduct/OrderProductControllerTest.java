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
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        MemberAdapter memberAdapter = (MemberAdapter) loginService.loadUserByUsername(authentication.getPrincipal().toString());
        log.info("member={}", memberAdapter);

        RequestOrderProductStatusSearchDto statusSearchDto = new RequestOrderProductStatusSearchDto();

        LocalDate st = LocalDate.of(2022, 03, 05);
        LocalDate ed = LocalDate.of(2023, 07, 11);
        statusSearchDto.setStartDate(st);
        statusSearchDto.setEndDate(ed);

        List<ResponseOrderProductDetailDto> responseOrderProductDetailDtos = orderProductController.orderList(statusSearchDto, memberAdapter);
        for (ResponseOrderProductDetailDto responseOrderProductDetailDto : responseOrderProductDetailDtos) {
            log.info("responseOrderProductDetailDto={}", responseOrderProductDetailDto.getProductName());
        }

    }

}