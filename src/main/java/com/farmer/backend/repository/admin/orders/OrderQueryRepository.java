package com.farmer.backend.repository.admin.orders;

import com.farmer.backend.dto.admin.orders.ResponseOrdersAndPaymentDto;
import com.farmer.backend.dto.admin.orders.ResponseOrdersDto;
import com.farmer.backend.dto.admin.orders.SearchOrdersCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderQueryRepository {
    Page<ResponseOrdersDto> findOrderList(Pageable pageable, SearchOrdersCondition searchCondition, String sortOrderCond);
    Page<ResponseOrdersDto> findOrderStatusList(Pageable pageable, SearchOrdersCondition searchCondition, String sortOrderCond);

    List<ResponseOrdersAndPaymentDto> findOrdersAndPayment(Long orderId);

    Page<ResponseOrdersDto> findAll(Pageable pageable, SearchOrdersCondition searchCondition);

}
