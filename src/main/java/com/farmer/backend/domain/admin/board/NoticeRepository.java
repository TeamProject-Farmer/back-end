package com.farmer.backend.domain.admin.board;

import com.farmer.backend.domain.admin.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice,Long>, BoardQueryRepository {

    Optional<Notice> findById(Long id);

}
