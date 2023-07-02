package com.farmer.backend.api.controller.product;

import com.farmer.backend.api.controller.product.response.ResponseProductDto;
import com.farmer.backend.api.controller.product.response.ResponseProductDtoList;
import com.farmer.backend.api.service.product.ProductService;
import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.domain.product.ProductOrderCondition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/product")
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
}
