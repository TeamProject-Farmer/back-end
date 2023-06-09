package com.farmer.backend.domain.product.productcategory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>, ProductCategoryQueryRepository {
}
