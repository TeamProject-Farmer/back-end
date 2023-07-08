package com.farmer.backend.domain.orders;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long>, OrderQueryRepository {

    List<Orders> findByMemberId(Long memberId);
}
