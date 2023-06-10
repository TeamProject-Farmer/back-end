package com.farmer.backend.repository.admin.board;

import com.farmer.backend.entity.Notice;
import com.farmer.backend.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice,Long>, BoardQueryRepository {

    Optional<Notice> findById(Long id);

}
