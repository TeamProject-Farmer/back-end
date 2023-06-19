package com.farmer.backend.repository.admin.payment;

import com.farmer.backend.entity.Payment;

import java.util.List;

public interface PaymentQueryRepository {
    List<Payment> getPaymentsByOrderId(Long orderId);
}
