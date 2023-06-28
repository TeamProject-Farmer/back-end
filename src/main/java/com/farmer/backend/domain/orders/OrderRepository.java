package com.farmer.backend.domain.orders;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long>, OrderQueryRepository {
}
