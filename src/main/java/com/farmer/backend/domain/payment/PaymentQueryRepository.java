package com.farmer.backend.domain.payment;

import com.farmer.backend.domain.payment.Payment;

import java.util.List;

public interface PaymentQueryRepository {
    List<Payment> getPaymentsByOrderId(Long orderId);
}
