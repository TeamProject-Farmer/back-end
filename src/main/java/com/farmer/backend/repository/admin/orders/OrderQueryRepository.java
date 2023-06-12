package com.farmer.backend.repository.admin.orders;

import com.farmer.backend.dto.admin.orders.ResponseOrdersDto;
import com.farmer.backend.dto.admin.orders.SearchOrdersCondition;
import com.farmer.backend.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderQueryRepository {
    Page<ResponseOrdersDto> findOrderList(Pageable pageable, SearchOrdersCondition searchCondition, OrderStatus sortOrderCond);
    Page<ResponseOrdersDto> findOrderStatusList(Pageable pageable, SearchOrdersCondition searchCondition, OrderStatus sortOrderCond);

    List<String> findOrderDetail(Long orderId);

}
