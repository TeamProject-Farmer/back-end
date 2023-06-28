package com.farmer.backend.api.controller.admin.product.response;

import com.farmer.backend.domain.product.Product;
import com.farmer.backend.domain.product.productcategory.ProductCategory;
import com.farmer.backend.domain.product.ProductSize;
import com.farmer.backend.api.controller.admin.options.response.ResponseOptionDto;
import lombok.*;

import java.util.List;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProductDto {

    private Long id;
    private Long categoryId;
    private String categoryName;
    private String name;
    private Integer stockQuantity;
    private Integer price;
    private Integer sellQuantity;
    private Integer discountRate;
    private List<ResponseOptionDto> options;
    private String thumbnailImg;
    private String brandName;
    private ProductSize size;
    private String description;
    private String detailImg1;
    private String detailImg2;
    private String detailImg3;
    private String detailImg4;
    private String detailImg5;

    public static ResponseProductDto getAllProductList(Product product) {
        return ResponseProductDto.builder()
                .id(product.getId())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .name(product.getName())
                .stockQuantity(product.getStockQuantity())
                .price(product.getPrice())
                .sellQuantity(product.getSellQuantity())
                .discountRate(product.getDiscountRate())
                .thumbnailImg(product.getThumbnailImg())
                .brandName(product.getBrandName())
                .size(product.getSize())
                .description(product.getDescription())
                .detailImg1(product.getDetailImg1())
                .detailImg2(product.getDetailImg2())
                .detailImg3(product.getDetailImg3())
                .detailImg4(product.getDetailImg4())
                .detailImg5(product.getDetailImg5())
                .build();
    }

    public static ResponseProductDto getOneProduct(Product product, List<ResponseOptionDto> options) {
        return ResponseProductDto.builder()
                .id(product.getId())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .name(product.getName())
                .stockQuantity(product.getStockQuantity())
                .price(product.getPrice())
                .sellQuantity(product.getSellQuantity())
                .discountRate(product.getDiscountRate())
                .options(options)
                .thumbnailImg(product.getThumbnailImg())
                .brandName(product.getBrandName())
                .size(product.getSize())
                .description(product.getDescription())
                .detailImg1(product.getDetailImg1())
                .detailImg2(product.getDetailImg2())
                .detailImg3(product.getDetailImg3())
                .detailImg4(product.getDetailImg4())
                .detailImg5(product.getDetailImg5())
                .build();
    }


    public static ResponseProductDto categoryList(ProductCategory category) {
        return ResponseProductDto.builder()
                .categoryId(category.getId())
                .categoryName(category.getName())
                .build();
    }
}
