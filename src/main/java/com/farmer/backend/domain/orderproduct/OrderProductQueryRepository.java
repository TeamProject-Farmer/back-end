package com.farmer.backend.domain.orderproduct;

import com.farmer.backend.api.controller.order.response.ResponseOrderDetailDto;

import java.util.List;

public interface OrderProductQueryRepository {
    List<ResponseOrderDetailDto> findOrderDetail(Long orderId);

}
