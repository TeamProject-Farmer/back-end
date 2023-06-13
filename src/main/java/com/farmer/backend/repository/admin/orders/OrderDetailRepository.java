package com.farmer.backend.repository.admin.orders;

import com.farmer.backend.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, OrderQueryRepository {

}
