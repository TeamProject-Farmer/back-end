package com.farmer.backend.domain.admin.faq.faqcategory;

import com.farmer.backend.domain.admin.board.BoardQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FaqCategoryRepository extends JpaRepository<FaqCategory, Long> , BoardQueryRepository {

    Optional<FaqCategory> findById(Long id);
}
