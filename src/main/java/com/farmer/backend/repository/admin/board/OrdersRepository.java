package com.farmer.backend.repository.admin.board;

import com.farmer.backend.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders,Long>, BoardQueryRepository {

    Optional<Orders> findById(Long id);
}
