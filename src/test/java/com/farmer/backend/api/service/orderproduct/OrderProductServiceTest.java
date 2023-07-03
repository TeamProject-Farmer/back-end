package com.farmer.backend.api.service.orderproduct;

import com.farmer.backend.api.controller.orderproduct.response.ResponseOrderProductDto;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.domain.orderproduct.OrderProduct;
import com.farmer.backend.domain.orderproduct.OrderProductQueryRepository;
import com.farmer.backend.domain.orderproduct.OrderProductRepository;
import com.farmer.backend.domain.orders.OrderRepository;
import com.farmer.backend.domain.orders.Orders;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("마이페이지 구매목록 기능")
    void shoppingList() {
        Member findMember = memberRepository.findByEmail("2020112112@dgu.ac.kr").orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        List<Orders> orderList = orderRepository.findByMemberId(findMember.getId());
        List<ResponseOrderProductDto> responseOrderProductDto = new ArrayList<>();

        for (Orders order : orderList) {
            List<OrderProduct> orderProducts = orderProductRepository.findTop6ByOrdersIdOrderByIdDesc(order.getId());
            for (OrderProduct orderProduct : orderProducts) {
                log.info("orderProduct={}", orderProduct.getProduct().getName());
            }
        }

    }

}