package com.farmer.backend.api.service.payment;

import com.farmer.backend.api.controller.payment.request.RequestPaymentInfoDto;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.domain.orderproduct.OrderProduct;
import com.farmer.backend.domain.orderproduct.OrderProductRepository;
import com.farmer.backend.domain.orders.OrderRepository;
import com.farmer.backend.domain.orders.Orders;
import com.farmer.backend.domain.payment.PaymentRepository;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.login.general.MemberAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    public void completePayment(RequestPaymentInfoDto paymentInfoDto, MemberAdapter member) {
        memberRepository.findByEmail(member.getUsername()).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Orders findOrder = orderRepository.findById(paymentInfoDto.getOrderId()).orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        int discountRate = orderProductRepository.findByOrdersId(findOrder.getId()).stream().map(orderProduct -> orderProduct.getProduct().getDiscountRate()).mapToInt(percent -> percent).sum();

    }
}
