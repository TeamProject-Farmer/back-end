package com.farmer.backend.service;

import com.farmer.backend.api.controller.options.response.ResponseOptionDto;
import com.farmer.backend.api.controller.product.response.ResponseProductDto;
import com.farmer.backend.api.controller.product.response.ResponseProductDtoList;
import com.farmer.backend.api.controller.product.response.ResponseShopBySizeProduct;
import com.farmer.backend.api.controller.productcategory.response.ResponseCategoryDto;
import com.farmer.backend.api.service.product.ProductService;
import com.farmer.backend.domain.options.OptionRepository;
import com.farmer.backend.domain.product.ProductOrderCondition;
import com.farmer.backend.domain.product.ProductQueryRepository;
import com.farmer.backend.domain.product.ProductRepository;
import com.farmer.backend.domain.product.ProductSize;
import com.farmer.backend.domain.product.productcategory.ProductCategoryRepository;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(value = false)
class ProductServiceTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductQueryRepository productQueryRepositoryImpl;
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    OptionRepository optionRepository;

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

    @Test
    @DisplayName("상품 카테고리 리스트")
    void productCategoryList() {
        List<ResponseCategoryDto> categoryList = productCategoryRepository.findAll().stream().map(ResponseCategoryDto::categoryList).collect(Collectors.toList());
        for (ResponseCategoryDto category : categoryList) {
            log.info("category={}", category.getCategoryName());
        }
    }

    @Test
    @DisplayName("베스트 상품 리스트")
    void bestProductList() {
        List<ResponseProductDtoList> bestProductList = productQueryRepositoryImpl.bestProductList();
        for (ResponseProductDtoList bestProduct : bestProductList) {
            log.info("bestProduct={}", bestProduct.getProductName());
        }
    }

    @Test
    @DisplayName("사이즈별 대표 상품 조회")
    void shopBySizeProductOne() {
        ResponseShopBySizeProduct shopBySizeProductOne = productQueryRepositoryImpl.findByShopBySizeProductOne(ProductSize.M);
        log.info("productOne={}", shopBySizeProductOne.getProductCategoryId());
    }

    @Test
    @DisplayName("상품 상세 조회")
    void productDetail() {
        Long productId = 5L;
        List<ResponseOptionDto> options = optionRepository.findByProductId(productId).stream().map(ResponseOptionDto::optionList).collect(Collectors.toList());
        ResponseProductDto productDto = productRepository.findById(productId).map(product -> ResponseProductDto.getOneProduct(product, options)).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        log.info("productDto={}", productDto.getName());
    }

}