package com.farmer.backend.domain.orders;

import com.farmer.backend.api.controller.order.response.ResponseOrderCompleteDto;
import com.farmer.backend.api.controller.order.response.ResponseOrdersAndPaymentDto;
import com.farmer.backend.api.controller.order.response.ResponseOrdersDto;
import com.farmer.backend.api.controller.order.request.SearchOrdersCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderQueryRepository {
    Page<ResponseOrdersDto> findOrderList(Pageable pageable, SearchOrdersCondition searchCondition, String sortOrderCond);
    Page<ResponseOrdersDto> findOrderStatusList(Pageable pageable, SearchOrdersCondition searchCondition, String sortOrderCond);

    List<ResponseOrdersAndPaymentDto> findOrdersAndPayment(Long orderId);

    Page<ResponseOrdersDto> findAll(Pageable pageable, SearchOrdersCondition searchCondition);

}
