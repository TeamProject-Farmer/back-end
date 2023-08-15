package com.farmer.backend.domain.coupon;

import com.farmer.backend.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findById(Long id);

}
