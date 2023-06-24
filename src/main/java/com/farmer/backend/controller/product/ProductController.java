package com.farmer.backend.controller.product;

import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.dto.admin.SortOrderCondition;
import com.farmer.backend.dto.admin.product.ResponseProductDto;
import com.farmer.backend.paging.PageRequest;
import com.farmer.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/product")
@Tag(name = "ProductController", description = "상품 페이지 API")
public class ProductController {
    private final ProductService productService;

    @ApiDocumentResponse
    @Operation(summary = "베스트 상품 리스트", description = "베스트 상품 리스트를 반환합니다. (판매량 기준)")
    @RequestMapping(method = RequestMethod.GET, value = "/best")
    public List<ResponseProductDto> getBestProducts(){

        return productService.getBestProductList();
    }

}
