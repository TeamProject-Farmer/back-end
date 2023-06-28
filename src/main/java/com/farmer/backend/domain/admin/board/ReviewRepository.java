package com.farmer.backend.domain.admin.board;

import com.farmer.backend.domain.product.productreview.ProductReviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository  extends JpaRepository<ProductReviews,Long>,BoardQueryRepository {

    Optional<ProductReviews> findById(Long reviewId);

}
