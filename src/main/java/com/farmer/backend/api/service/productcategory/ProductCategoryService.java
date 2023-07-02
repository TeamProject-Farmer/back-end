package com.farmer.backend.api.service.productcategory;

import com.farmer.backend.api.controller.productcategory.response.ResponseCategoryDto;
import com.farmer.backend.domain.product.productcategory.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    /**
     * 상품 카테고리 리스트
     * @return
     */
    @Transactional(readOnly = true)
    public List<ResponseCategoryDto> productCategoryList() {
        List<ResponseCategoryDto> productCategoryList = productCategoryRepository.findAll().stream().map(category -> ResponseCategoryDto.categoryList(category)).collect(Collectors.toList());
        return productCategoryList;
    }
}
