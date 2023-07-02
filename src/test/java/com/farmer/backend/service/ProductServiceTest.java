package com.farmer.backend.service;

import com.farmer.backend.api.controller.product.response.ResponseProductDtoList;
import com.farmer.backend.api.service.product.ProductService;
import com.farmer.backend.domain.product.ProductOrderCondition;
import com.farmer.backend.domain.product.ProductQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(value = false)
class ProductServiceTest {

    @Autowired
    ProductQueryRepository productQueryRepositoryImpl;

    @Test
    @DisplayName("상품 전체 리스트")
    void productList() {
        List<ResponseProductDtoList> productList = productQueryRepositoryImpl.productList(ProductOrderCondition.NEWS);
        for (ResponseProductDtoList product : productList) {
            log.info("product={}", product.getProductName());
        }
    }

    @Test
    @DisplayName("MD PICK 상품 리스트")
    void mdPickList() {
        List<ResponseProductDtoList> mdPickList = productQueryRepositoryImpl.mdPickList();
        for (ResponseProductDtoList product : mdPickList) {
            log.info("product={}", product.getProductName());
        }
    }

}