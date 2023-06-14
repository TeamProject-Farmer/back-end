package com.farmer.backend.repository.admin.orders;

import com.farmer.backend.dto.admin.orders.ResponseOrderDetailDto;
import com.farmer.backend.dto.admin.orders.ResponseOrdersDto;
import com.farmer.backend.dto.admin.orders.SearchOrdersCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderQueryRepository {
    Page<ResponseOrdersDto> findOrderList(Pageable pageable, SearchOrdersCondition searchCondition, String sortOrderCond);
    Page<ResponseOrdersDto> findOrderStatusList(Pageable pageable, SearchOrdersCondition searchCondition, String sortOrderCond);

    List<ResponseOrderDetailDto> findOrderDetail(Long orderId);

    Page<ResponseOrdersDto> findAll(Pageable pageable, SearchOrdersCondition searchCondition);
}
