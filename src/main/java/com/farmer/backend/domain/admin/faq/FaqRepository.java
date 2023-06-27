package com.farmer.backend.domain.admin.faq;

import com.farmer.backend.domain.admin.board.BoardQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FaqRepository  extends JpaRepository<Faq,Long>, BoardQueryRepository {

    Optional<Faq> findById(Long id);
}
