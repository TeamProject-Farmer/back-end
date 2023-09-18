package com.farmer.backend.domain.orders;

import com.farmer.backend.api.controller.admin.order.response.ResponseOrderDetailDto;
import com.farmer.backend.api.controller.admin.order.response.ResponseOrderListDto;
import com.farmer.backend.api.controller.user.order.response.ResponseOrdersAndPaymentDto;
import com.farmer.backend.api.controller.user.order.response.ResponseOrdersDto;
import com.farmer.backend.api.controller.user.order.request.SearchOrdersCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderQueryRepository {
    List<ResponseOrderListDto> findOrderList(SearchOrdersCondition searchCondition);

    List<ResponseOrdersAndPaymentDto> findOrdersAndPayment(Long orderId);


    ResponseOrderDetailDto findByOrderDetail(Long orderId);
}
