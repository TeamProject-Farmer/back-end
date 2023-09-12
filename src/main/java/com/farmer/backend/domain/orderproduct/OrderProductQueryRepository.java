package com.farmer.backend.domain.orderproduct;

import com.farmer.backend.api.controller.user.order.response.ResponseOrderDetailDto;
import com.farmer.backend.api.controller.user.orderproduct.request.RequestOrderProductStatusSearchDto;
import com.farmer.backend.api.controller.user.orderproduct.response.ResponseOrderProductDetailDto;

import java.util.List;

public interface OrderProductQueryRepository {
    List<ResponseOrderDetailDto> findOrderDetail(Long orderId);

    List<ResponseOrderProductDetailDto> findUserOrderProductDetail(RequestOrderProductStatusSearchDto statusSearchDto, Long id);
}
