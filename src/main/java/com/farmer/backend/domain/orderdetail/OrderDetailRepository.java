package com.farmer.backend.domain.orderdetail;

import com.farmer.backend.domain.orders.OrderQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, OrderQueryRepository {

}
