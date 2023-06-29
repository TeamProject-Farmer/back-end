package com.farmer.backend.domain.orderproduct;

import com.farmer.backend.domain.orders.OrderQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, OrderQueryRepository {

}
