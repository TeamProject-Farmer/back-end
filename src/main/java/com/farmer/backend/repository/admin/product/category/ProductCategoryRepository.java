package com.farmer.backend.repository.admin.product.category;

import com.farmer.backend.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>, ProductCategoryQueryRepository {
}
