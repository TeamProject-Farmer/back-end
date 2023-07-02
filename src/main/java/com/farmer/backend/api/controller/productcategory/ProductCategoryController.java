package com.farmer.backend.api.controller.productcategory;

import com.farmer.backend.api.service.productcategory.ProductCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/product-category")
@Tag(name = "ProductCategoryController", description = "상품 카테고리 API")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

}
