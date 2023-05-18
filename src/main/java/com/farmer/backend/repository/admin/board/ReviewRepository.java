package com.farmer.backend.repository.admin.board;

import com.farmer.backend.entity.Product_reviews;
import com.farmer.backend.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository  extends JpaRepository<Product_reviews,Long> {

    Optional<Product_reviews> findById(Long reviewId);

}
