package com.farmer.backend.repository.admin.product;

import com.farmer.backend.dto.admin.SortOrderCondition;
import com.farmer.backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductQueryRepository {
    Page<Product> findAll(Pageable pageable, String productName, String orderCondition);
}
