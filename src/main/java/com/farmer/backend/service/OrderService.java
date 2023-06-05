package com.farmer.backend.service;

import com.farmer.backend.dto.admin.orders.ResponseOrdersDto;
import com.farmer.backend.dto.admin.orders.SearchOrdersCondition;
import com.farmer.backend.entity.OrderStatus;
import com.farmer.backend.entity.Orders;
import com.farmer.backend.repository.admin.orders.OrderQueryRepository;
import com.farmer.backend.repository.admin.orders.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepositoryImpl;

    @Transactional(readOnly = true)
    public List<ResponseOrdersDto> orderList(Pageable pageable, SearchOrdersCondition ordersCondition, String sortOrderCond) {
        return orderQueryRepositoryImpl.findAll(pageable, ordersCondition, sortOrderCond);
    }
}
