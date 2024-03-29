package com.farmer.backend.api.service.orderproduct;

import com.farmer.backend.api.controller.user.order.response.ResponseOrderInfoDto;
import com.farmer.backend.api.controller.user.orderproduct.request.RequestOrderProductStatusSearchDto;
import com.farmer.backend.api.controller.user.orderproduct.response.ResponseOrderProductDetailDto;
import com.farmer.backend.api.controller.user.orderproduct.response.ResponseOrderProductDto;
import com.farmer.backend.domain.deliveryaddress.DeliveryAddress;
import com.farmer.backend.domain.deliveryaddress.DeliveryAddressRepository;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.domain.orderproduct.OrderProduct;
import com.farmer.backend.domain.orderproduct.OrderProductQueryRepository;
import com.farmer.backend.domain.orderproduct.OrderProductRepository;
import com.farmer.backend.domain.orders.OrderRepository;
import com.farmer.backend.domain.orders.OrderStatus;
import com.farmer.backend.domain.orders.Orders;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
class OrderProductServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    OrderProductQueryRepository orderProductQueryRepositoryImpl;
    @Autowired
    DeliveryAddressRepository deliveryAddressRepository;

    @Test
    @DisplayName("마이페이지 구매목록 기능")
    void shoppingList() {
        Member findMember = memberRepository.findByEmail("codms7020@naver.com").orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        List<Orders> orderList = orderRepository.findByMemberId(findMember.getId());
        List<ResponseOrderProductDto> responseOrderProductDto = new ArrayList<>();
        String productName = null;
        for (Orders order : orderList) {
            List<OrderProduct> orderProducts = orderProductRepository.findTop6ByOrdersIdOrderByIdDesc(order.getId());
            for (OrderProduct orderProduct : orderProducts) {
                log.info("orderProduct={}", orderProduct.getProduct().getName());
                productName = orderProduct.getProduct().getName();
            }
        }

        assertThat(productName).isNotNull();
    }

    @Test
    @DisplayName("마이페이지 주문내역 조회 기능")
    void orderList() {
        RequestOrderProductStatusSearchDto requestOrderProductStatusSearchDto = new RequestOrderProductStatusSearchDto();
        Member findMember = memberRepository.findByEmail("2020112112@dgu.ac.kr").orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        requestOrderProductStatusSearchDto.setOrderStatus(OrderStatus.DONE);
        LocalDate startDate = LocalDate.of(2022, 05, 03);
        LocalDate endDate = LocalDate.of(2023, 05, 03);

        requestOrderProductStatusSearchDto.setStartDate(startDate);
        requestOrderProductStatusSearchDto.setEndDate(endDate);
        List<Orders> orderList = orderRepository.findByMemberId(findMember.getId());
        List<ResponseOrderProductDetailDto> orderProductList = new ArrayList<>();
        for (Orders orders : orderList) {
            orderProductList = orderProductQueryRepositoryImpl.findUserOrderProductDetail(requestOrderProductStatusSearchDto, orders.getId());
        }

        for (ResponseOrderProductDetailDto res : orderProductList) {
            log.info("res={}", res.getProductName());
        }
        assertThat(orderProductList).isNotNull();
    }

    @Test
    @DisplayName("주문자 배송지 정보 조회")
    void orderForm() {
        Member member = memberRepository.findByEmail("kce2360@naver.com").orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByMember(member);
        ResponseOrderInfoDto orderInfo = null;
        if (deliveryAddress != null) {
            orderInfo = ResponseOrderInfoDto.builder()
                    .username(deliveryAddress.getName())
                    .zipcode(deliveryAddress.getZipcode())
                    .address(deliveryAddress.getAddress())
                    .addressDetail(deliveryAddress.getAddressDetail())
                    .zipcode(deliveryAddress.getZipcode())
                    .build();
        }

        log.info("orderInfo={}", orderInfo);
        assertThat(orderInfo).isNull();
    }

}