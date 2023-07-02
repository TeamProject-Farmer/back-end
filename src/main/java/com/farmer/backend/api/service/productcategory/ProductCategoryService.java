package com.farmer.backend.api.service.productcategory;

import com.farmer.backend.domain.product.productcategory.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
}
