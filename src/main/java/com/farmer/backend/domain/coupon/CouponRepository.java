package com.farmer.backend.domain.coupon;

import com.farmer.backend.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
