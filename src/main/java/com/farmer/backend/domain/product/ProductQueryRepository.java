package com.farmer.backend.domain.product;

import com.farmer.backend.api.controller.product.response.ResponseProductDtoList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductQueryRepository {
    Page<Product> findAll(Pageable pageable, String productName, String orderCondition);

    List<ResponseProductDtoList> productList(ProductOrderCondition orderCondition);

    List<ResponseProductDtoList> mdPickList();

    List<ResponseProductDtoList> bestProductList();
}
