package com.farmer.backend.api.controller.product;

import com.farmer.backend.api.controller.product.response.ResponseProductDto;
import com.farmer.backend.api.controller.product.response.ResponseProductDtoList;
import com.farmer.backend.api.controller.product.response.ResponseShopBySizeProduct;
import com.farmer.backend.api.service.product.ProductService;
import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.domain.product.ProductOrderCondition;
import com.farmer.backend.domain.product.ProductSize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/main/product")
@Tag(name = "ProductController", description = "상품 API")
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 전체 리스트
     */
    @ApiDocumentResponse
    @Operation(summary = "상품 전체 리스트", description = "상품 전체 리스트를 출력합니다.")
    @GetMapping
    public List<ResponseProductDtoList> productList(ProductOrderCondition orderCondition) {
        return productService.productList(orderCondition);
    }

    /**
     * MD PICK 상품 리스트
     */
    @ApiDocumentResponse
    @Operation(summary = "MD PICK 리스트", description = "MD PICK 전체 리스트를 출력합니다.")
    @GetMapping("/md-pick")
    public List<ResponseProductDtoList> mdPickList() {
        return productService.mdPickList();
    }

    /**
     * 베스트 식물 리스트
     */
    @ApiDocumentResponse
    @Operation(summary = "베스트 식물 리스트", description = "베스트 식물 리스트를 10위까지 조회합니다.")
    @GetMapping("/best-product")
    public List<ResponseProductDtoList> bestProductList() {
        return productService.bestProductList();
    }

    /**
     * SHOP BY SIZE(사이즈 클릭 시 해당 상품 대표 이미지 노출)
     */
    @ApiDocumentResponse
    @Operation(summary = "SHOP BY SIZE 상품", description = "크기별 대표 상품 이미지 한건을 노출합니다.")
    @GetMapping("/shop-by-size")
    public ResponseEntity shopBySize(ProductSize size) {
        ResponseShopBySizeProduct shopBySizeProduct = productService.shopBySizeOne(size);
        return ResponseEntity.ok(shopBySizeProduct);
    }

    /**
     * 상품 상세 페이지
     */
    @ApiDocumentResponse
    @Operation(summary = "상품 상세 페이지", description = "상품 한건의 상세 정보를 확인합니다.")
    @GetMapping("/detail/{productId}")
    public ResponseEntity productDetail(@PathVariable Long productId) {
        ResponseProductDto productDto = productService.productDetail(productId);
        return ResponseEntity.ok(productDto);
    }

}
