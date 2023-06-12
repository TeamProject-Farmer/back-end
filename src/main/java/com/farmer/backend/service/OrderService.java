package com.farmer.backend.service;

import com.farmer.backend.dto.admin.orders.ResponseOrdersDto;
import com.farmer.backend.dto.admin.orders.SearchOrdersCondition;
import com.farmer.backend.entity.OrderStatus;
import com.farmer.backend.entity.Orders;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.repository.admin.orders.OrderDetailQueryRepository;
import com.farmer.backend.repository.admin.orders.OrderDetailRepository;
import com.farmer.backend.repository.admin.orders.OrderQueryRepository;
import com.farmer.backend.repository.admin.orders.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepositoryImpl;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailQueryRepository orderDetailQueryRepositoryImpl;

    /**
     * 주문 전체 리스트
     * @param pageable 페이징
     * @param searchCondition 주문 정보 검색
     * @param sortOrderCond 주문 상태 정렬
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ResponseOrdersDto> orderList(Pageable pageable, SearchOrdersCondition searchCondition, OrderStatus sortOrderCond) {
        if (sortOrderCond.equals(OrderStatus.ORDER)) {
            return orderQueryRepositoryImpl.findOrderList(pageable, searchCondition, sortOrderCond);
        }
        return orderQueryRepositoryImpl.findOrderStatusList(pageable, searchCondition, sortOrderCond);
    }

    @Transactional(readOnly = true)
    public List<Object> orderInfo(Long orderId) {
        return null;
    }
}
