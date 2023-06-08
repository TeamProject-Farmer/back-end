package com.farmer.backend.repository.admin.board;

import com.farmer.backend.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FaqRepository  extends JpaRepository<Faq,Long>, BoardQueryRepository{

    Optional<Faq> findById(Long id);
}
