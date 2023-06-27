package com.farmer.backend.domain.orderdetail;

import com.farmer.backend.api.controller.admin.order.response.ResponseOrderDetailDto;

import java.util.List;

public interface OrderDetailQueryRepository {
    List<ResponseOrderDetailDto> findOrderDetail(Long orderId);

}
