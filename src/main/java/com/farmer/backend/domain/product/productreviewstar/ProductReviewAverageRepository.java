package com.farmer.backend.domain.product.productreviewstar;

import com.farmer.backend.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductReviewAverageRepository extends JpaRepository<ProductReviewAverage, Long> {

    Optional<ProductReviewAverage> findByProductId(Long productId);

}
