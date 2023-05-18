package com.farmer.backend.repository.admin.board;

import com.farmer.backend.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QnARepository extends JpaRepository<Qna,Long> , BoardQueryRepository {

    Optional<Qna> findById(Long id);

}
