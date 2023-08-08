package com.farmer.backend.domain.product.productreview;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductReviewRepository extends JpaRepository<ProductReviews, Long> {

    List<ProductReviews> findByOrderProductProductId(Long ProductId);
    Optional<ProductReviews> findById(Long productId);

}
