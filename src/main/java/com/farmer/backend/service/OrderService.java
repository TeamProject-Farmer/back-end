package com.farmer.backend.service;

import com.farmer.backend.dto.admin.orders.*;
import com.farmer.backend.entity.Orders;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.repository.admin.orders.OrderDetailQueryRepository;
import com.farmer.backend.repository.admin.orders.OrderDetailRepository;
import com.farmer.backend.repository.admin.orders.OrderQueryRepository;
import com.farmer.backend.repository.admin.orders.OrderRepository;
import com.farmer.backend.repository.admin.payment.PaymentQueryRepository;
import com.farmer.backend.repository.admin.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepositoryImpl;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailQueryRepository orderDetailQueryRepositoryImpl;
    private final PaymentRepository paymentRepository;
    private final PaymentQueryRepository paymentQueryRepositoryImpl;

    /**
     * 주문 전체 리스트
     * @param pageable 페이징
     * @param searchCondition 주문 정보 검색
     * @param sortOrderCond 주문 상태 정렬
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ResponseOrdersDto> orderList(Pageable pageable, SearchOrdersCondition searchCondition, String sortOrderCond) {
        if (Objects.isNull(sortOrderCond)) {
            return orderQueryRepositoryImpl.findAll(pageable, searchCondition);
        } else if (sortOrderCond.toString().equals("ORDER")) {
            return orderQueryRepositoryImpl.findOrderList(pageable, searchCondition, sortOrderCond);
        }
        return orderQueryRepositoryImpl.findOrderStatusList(pageable, searchCondition, sortOrderCond);
    }

    /**
     * 주문 상세 조회
     * @param orderId 주문 일련번호
     * @return
     */
    @Transactional(readOnly = true)
    public List<Object> orderInfo(Long orderId) {
        List<Object> orderDetailContent = new ArrayList<>();
        Map<String, List<ResponseOrdersAndPaymentDto>> ordersAndPaymentMap = new HashMap<>();
        Map<String, List<ResponseOrderDetailDto>> orderDetailMap = new HashMap<>();
        List<ResponseOrdersAndPaymentDto> ordersAndPayment = orderQueryRepositoryImpl.findOrdersAndPayment(orderId);
        List<ResponseOrderDetailDto> orderDetail = orderDetailQueryRepositoryImpl.findOrderDetail(orderId);

        ordersAndPaymentMap.put("주문 결제정보", ordersAndPayment);
        orderDetailMap.put("주문상품 상세정보", orderDetail);
        orderDetailContent.add(ordersAndPaymentMap);
        orderDetailContent.add(orderDetailMap);

        return orderDetailContent;
    }
    @Transactional
    public void orderStatusUpdate(Long orderId, String orderStatus) {
        orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND)).orderStatusUpdateAction(orderStatus);
    }

    /**
     * 주문 삭제
     * @param orderId 주문 일련번호
     * @return
     */
    @Transactional
    public void orderRemove(Long orderId) {
        Orders findOrder = orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        orderRepository.delete(findOrder);
    }

}
