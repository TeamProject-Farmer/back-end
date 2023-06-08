package com.farmer.backend.repository.admin.board;

import com.farmer.backend.entity.FaqCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FaqCategoryRepository extends JpaRepository<FaqCategory, Long> , BoardQueryRepository {

    Optional<FaqCategory> findById(Long id);
}
