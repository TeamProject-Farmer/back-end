package com.farmer.backend.repository.admin.orders;

import com.farmer.backend.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long>, OrderQueryRepository {
}
