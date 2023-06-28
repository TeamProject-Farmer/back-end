package com.farmer.backend.domain.admin.board;

import com.farmer.backend.domain.admin.qna.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QnARepository extends JpaRepository<Qna,Long> , BoardQueryRepository {

    Optional<Qna> findById(Long id);

}
