package com.farmer.backend.service;

import com.farmer.backend.api.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(value = false)
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    @DisplayName("상품 등록")
    void createProduct() {
        Map<Long, String> categoryList = productService.getCategoryList();


    }

}