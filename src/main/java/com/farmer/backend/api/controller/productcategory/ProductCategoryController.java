package com.farmer.backend.api.controller.productcategory;

import com.farmer.backend.api.controller.productcategory.response.ResponseCategoryDto;
import com.farmer.backend.api.controller.productcategory.response.ResponseProductCategoryListDto;
import com.farmer.backend.api.service.productcategory.ProductCategoryService;
import com.farmer.backend.config.ApiDocumentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/main/product-category")
@Tag(name = "ProductCategoryController", description = "상품 카테고리 API")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    /**
     * 상품 카테고리 리스트
     */
    @ApiDocumentResponse
    @Operation(summary = "상품 카테고리 리스트", description = "상품 카테고리 리스트를 출력합니다.")
    @GetMapping
    public List<ResponseCategoryDto> productCategoryList() {
        List<ResponseCategoryDto> productCategoryList = productCategoryService.productCategoryList();
        return productCategoryList;
    }
}
