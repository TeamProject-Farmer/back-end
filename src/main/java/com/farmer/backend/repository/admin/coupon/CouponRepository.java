package com.farmer.backend.repository.admin.coupon;

import com.farmer.backend.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
