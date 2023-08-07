package com.farmer.backend.domain.orderproduct;

import com.farmer.backend.domain.orders.OrderQueryRepository;
import com.farmer.backend.domain.orders.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, OrderQueryRepository {

    List<OrderProduct> findByOrdersId(Long orderId);

    Optional<OrderProduct> findByProductId(Long aLong);

    List<OrderProduct> findTop6ByOrdersIdOrderByIdDesc(Long orderId);
}
