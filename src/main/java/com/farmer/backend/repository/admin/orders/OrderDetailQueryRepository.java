package com.farmer.backend.repository.admin.orders;

import com.farmer.backend.dto.admin.orders.ResponseOrderDetailDto;

import java.util.List;

public interface OrderDetailQueryRepository {
    List<ResponseOrderDetailDto> findOrderDetail(Long orderId);

}
