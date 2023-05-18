package com.farmer.backend.service;

import com.farmer.backend.dto.admin.product.RequestProductDto;
import com.farmer.backend.dto.admin.product.ResponseCategoryDto;
import com.farmer.backend.dto.admin.product.ResponseProductDto;
import com.farmer.backend.entity.ProductCategory;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.repository.admin.product.ProductQueryRepository;
import com.farmer.backend.repository.admin.product.ProductRepository;
import com.farmer.backend.repository.admin.product.category.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepositoryImpl;
    private final ProductCategoryRepository categoryRepository;


    /**
     * 상품 전체 리스트
     * @param pageable 페이징
     * @param productName 상품명 검색
     * @param orderCondition 정렬 순서
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ResponseProductDto> productList(Pageable pageable, String productName, String orderCondition) {
        return productQueryRepositoryImpl.findAll(pageable, productName, orderCondition).map(ResponseProductDto::getAllProductList);
    }

    @Transactional(readOnly = true)
    public ResponseProductDto productOne(Long productId) {
        return productRepository.findById(productId).map(ResponseProductDto::getAllProductList).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Map<Long, String> getCategoryList() {
        Map<Long, String> categories = new HashMap<>();
        List<ProductCategory> findCategoryList = categoryRepository.findAll();
        findCategoryList.stream().map(fc -> categories.put(fc.getId(), fc.getName())).collect(Collectors.toList());
        return categories;
    }

    @Transactional
    public void registerProduct(RequestProductDto productDto) {
        productRepository.save(productDto.toEntity());
    }


}
