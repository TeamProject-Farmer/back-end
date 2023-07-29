package com.farmer.backend.domain.options;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Options, Long> {
    List<Options> findByProductId(Long productId);
}
