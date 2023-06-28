package com.farmer.backend.domain.product;

import com.farmer.backend.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryRepository {
    Page<Product> findAll(Pageable pageable, String productName, String orderCondition);
}
