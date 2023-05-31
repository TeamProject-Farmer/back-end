package com.farmer.backend.service;

import com.farmer.backend.dto.admin.orders.ResponseOrdersDto;
import com.farmer.backend.repository.admin.orders.OrderQueryRepository;
import com.farmer.backend.repository.admin.orders.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepositoryImpl;

    @Transactional(readOnly = true)
    public Page<ResponseOrdersDto> orderList(PageRequest of, String memberName, String fieldName) {
        return null;
    }
}
