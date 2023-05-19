package com.farmer.backend.service;

import com.farmer.backend.dto.admin.member.ResponseMemberDto;
import com.farmer.backend.dto.admin.product.RequestProductDto;
import com.farmer.backend.dto.admin.product.ResponseCategoryDto;
import com.farmer.backend.dto.admin.product.ResponseProductDto;
import com.farmer.backend.entity.Member;
import com.farmer.backend.entity.Options;
import com.farmer.backend.entity.Product;
import com.farmer.backend.entity.ProductCategory;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.repository.admin.product.OptionRepository;
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
    private final OptionRepository optionRepository;


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

    /**
     * 상품 단건 조회
     * @param productId 상품 고유번호
     * @return
     */
    @Transactional(readOnly = true)
    public ResponseProductDto productOne(Long productId) {
        return productRepository.findById(productId).map(ResponseProductDto::getAllProductList).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    /**
     * 상품 전체 카테고리 리스트 조회
     * @return
     */
    @Transactional(readOnly = true)
    public Map<Long, String> getCategoryList() {
        Map<Long, String> categories = new HashMap<>();
        List<ProductCategory> findCategoryList = categoryRepository.findAll();
        findCategoryList.stream().map(fc -> categories.put(fc.getId(), fc.getName())).collect(Collectors.toList());
        return categories;
    }

    /**
     * 상품 등록
     * @param productDto 상품 등록 폼
     */
    @Transactional
    public void registerProduct(RequestProductDto productDto) {
        productRepository.save(productDto.toEntity());
    }

    /**
     * 상품 수정
     * @param productId 상품 고유번호
     * @param productDto 상품 수정폼
     */
    @Transactional
    public void updateActionProduct(Long productId, RequestProductDto productDto) {
        productRepository.findById(productId).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND)).productUpdate(productDto);
    }

    /**
     * 상품 삭제
     * @param productIds 상품 고유번호
     */
    @Transactional
    public void deleteProduct(Long[] productIds) {
        for (Long productId : productIds) {
            Product product = productRepository.findById(productId).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
            productRepository.delete(product);
        }
    }
}
