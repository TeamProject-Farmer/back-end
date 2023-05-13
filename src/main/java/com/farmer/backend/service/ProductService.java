package com.farmer.backend.service;

import com.farmer.backend.repository.admin.product.ProductQueryRepository;
import com.farmer.backend.repository.admin.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepositoryImpl;


}
