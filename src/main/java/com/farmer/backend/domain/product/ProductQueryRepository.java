package com.farmer.backend.domain.product;

import com.farmer.backend.api.controller.product.response.ResponseProductDtoList;
import com.farmer.backend.api.controller.product.response.ResponseShopBySizeProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductQueryRepository {
    Page<Product> findAll(Pageable pageable, String productName, String orderCondition);

    Page<ResponseProductDtoList> productList(Long categoryId, Pageable pageable, ProductOrderCondition orderCondition);

    List<ResponseProductDtoList> eventProductList(ProductDivision division);

    List<ResponseProductDtoList> bestProductList();

    ResponseShopBySizeProduct findByShopBySizeProductOne(ProductSize size);

}
