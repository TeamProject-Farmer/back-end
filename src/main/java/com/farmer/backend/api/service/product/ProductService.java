package com.farmer.backend.api.service.product;

import com.farmer.backend.api.controller.user.options.request.RequestOptionDto;
import com.farmer.backend.api.controller.user.options.response.ResponseOptionDto;
import com.farmer.backend.api.controller.user.product.request.RequestProductDto;
import com.farmer.backend.api.controller.user.product.response.ResponseProductDto;
import com.farmer.backend.api.controller.user.product.response.ResponseProductDtoList;
import com.farmer.backend.api.controller.user.product.response.ResponseReviewAndQnaDto;
import com.farmer.backend.api.controller.user.product.response.ResponseShopBySizeProduct;
import com.farmer.backend.api.service.review.ReviewService;
import com.farmer.backend.domain.options.Options;
import com.farmer.backend.domain.product.*;
import com.farmer.backend.domain.product.productcategory.ProductCategory;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.domain.options.OptionRepository;
import com.farmer.backend.domain.product.productcategory.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepositoryImpl;
    private final ProductCategoryRepository categoryRepository;
    private final OptionRepository optionRepository;
    private final ReviewService reviewService;


    /**
     * 쇼핑몰 상품 리스트
     * @param orderCondition 정렬조건
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ResponseProductDtoList> productList(Long categoryId, Pageable pageable, ProductOrderCondition orderCondition) {
        Page<ResponseProductDtoList> productList = productQueryRepositoryImpl.productList(categoryId, pageable, orderCondition);
        return productList;
    }

    /**
     * 쇼핑몰 MD PICK 상품 리스트
     * @return
     */
    @Transactional(readOnly = true)
    public List<ResponseProductDtoList> mdPickList(ProductDivision division) {
        List<ResponseProductDtoList> mdpickList = productQueryRepositoryImpl.eventProductList(division);
        return mdpickList;
    }

    @Transactional(readOnly = true)
    public List<ResponseProductDtoList> planProductList(ProductDivision division) {
        List<ResponseProductDtoList> planProductList = productQueryRepositoryImpl.eventProductList(division);
        return planProductList;
    }

    /**
     * 쇼핑몰 베스트 식물 리스트
     * @return
     */
    @Transactional(readOnly = true)
    public List<ResponseProductDtoList> bestProductList() {
        List<ResponseProductDtoList> bestProductList = productQueryRepositoryImpl.bestProductList();
        return bestProductList;
    }

    /**
     * 쇼핑몰 크기별 대표 상품 조회
     * @param size
     * @return
     */
    @Transactional(readOnly = true)
    public ResponseShopBySizeProduct shopBySizeOne(ProductSize size) {
        ResponseShopBySizeProduct productBySize = productQueryRepositoryImpl.findByShopBySizeProductOne(size);
        return productBySize;
    }

    /**
     * 쇼핑몰 크기별 상품 리스트 조회
     *
     * @param size
     * @param pageable
     * @param orderCondition
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ResponseProductDtoList> shopBySizeProductList(ProductSize size, Pageable pageable, ProductOrderCondition orderCondition) {
        return productQueryRepositoryImpl.findByShopBySizeProductList(size, pageable, orderCondition);
    }

    /**
     * 쇼핑몰 상품 상세 페이지
     * @param productId 상품 일련번호
     * @return
     */
    @Transactional(readOnly = true)
    public ResponseProductDto productDetail(Long productId) {
        List<ResponseOptionDto> options = optionRepository.findByProductId(productId).stream().map(ResponseOptionDto::optionList).collect(Collectors.toList());
        ResponseProductDto productDto = productRepository.findById(productId).map(product -> ResponseProductDto.getOneProduct(product, options)).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        return productDto;
    }

    /**
     * 상품 전체 리스트(어드민)
     * @param pageable 페이징
     * @param productName 상품명 검색
     * @param orderCondition 정렬 순서
     * @return Page<ResponseProductDto>
     */
    @Transactional(readOnly = true)
    public Page<ResponseProductDto> productList(Pageable pageable, String productName, String orderCondition) {
        return productQueryRepositoryImpl.findAll(pageable, productName, orderCondition).map(ResponseProductDto::getAllProductList);
    }

    /**
     * 상품 단건 조회
     * @param productId 상품 고유번호
     * @return ResponseProductDto
     */
    @Transactional(readOnly = true)
    public ResponseProductDto productOne(Long productId) {
        List<ResponseOptionDto> byProductId = optionRepository.findByProductId(productId).stream().map(ResponseOptionDto::optionList).collect(Collectors.toList());
        return productRepository.findById(productId).map(p -> ResponseProductDto.getOneProduct(p, byProductId)).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    /**
     * 상품 전체 카테고리 리스트 조회
     * @return categories
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
     * @param productId  상품 고유번호
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
            List<Options> findOptions = optionRepository.findByProductId(productId);
            for (Options findOption : findOptions) {
                optionRepository.delete(findOption);
            }
        }
    }

    /**
     * 옵션 등록
     * @param optionDto 옵션 폼
     */
    @Transactional
    public void addOptionAction(RequestOptionDto optionDto) {
        optionRepository.save(optionDto.toEntity());
    }

    /**
     * 옵션 수정
     * @param optionId 상품 일련번호
     * @param optionDto 옵션 폼
     */
    @Transactional
    public void updateActionOption(Long optionId, RequestOptionDto optionDto) {
        optionRepository.findById(optionId).orElseThrow(() -> new CustomException(ErrorCode.OPTION_NOT_FOUND)).optionUpdate(optionDto);
    }

    /**
     * 옵션 삭제
     * @param optionId 옵션 일련번호
     */
    @Transactional
    public void removeOption(Long optionId) {
        Options option = optionRepository.findById(optionId).orElseThrow(() -> new CustomException(ErrorCode.OPTION_NOT_FOUND));
        optionRepository.delete(option);
    }

    /**
     * 상품 검색
     * @param pageable 페이징
     * @param productName 상품명 정보
     * @return Page<ResponseProductDto>
     */
    @Transactional(readOnly = true)
    public Page<ResponseProductDto> searchActionProductList(PageRequest pageable, String productName) {
        return productQueryRepositoryImpl.findAll(pageable, productName, null).map(ResponseProductDto::getAllProductList);
    }

    /**
     * 상품 리뷰 , 문의 사항 개수, 리뷰 평점
     * @return
     */
    public ResponseReviewAndQnaDto reviewAndQna(Long productId) {
        Double reviewAverage= reviewService.reviewAverage(productId).getAverageStarRating();
        return productQueryRepositoryImpl.reviewAndQnaInfo(productId,reviewAverage);
    }
}
