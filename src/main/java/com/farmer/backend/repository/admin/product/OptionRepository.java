package com.farmer.backend.repository.admin.product;

import com.farmer.backend.entity.Options;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OptionRepository extends JpaRepository<Options, Long> {
    List<Options> findByProductId(Long productId);
}
