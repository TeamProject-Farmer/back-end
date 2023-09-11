package com.farmer.backend.api.service.order;

import com.farmer.backend.api.controller.admin.order.response.ResponseOrderListDto;
import com.farmer.backend.api.controller.user.order.request.SearchOrdersCondition;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.domain.orders.OrderRepository;
import com.farmer.backend.domain.orders.Orders;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Slf4j
class OrderServiceTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("전체 주문조회")
    public void orderList() throws Exception {
        SearchOrdersCondition searchOrdersCondition = new SearchOrdersCondition(null, null);
        searchOrdersCondition.setOrderName("채");
        List<ResponseOrderListDto> orderList = orderRepository.findOrderList(searchOrdersCondition);
        List<Orders> findMemberOrderList = orderList.stream().map(
                order -> orderRepository.findById(order.getOrderId()).orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND))
        ).collect(Collectors.toList());

        for (Orders orders : findMemberOrderList) {
            assertThat(orders.getMember().getNickname()).contains(searchOrdersCondition.getOrderName());
        }

        assertThat(findMemberOrderList).isNotNull();
    }

}