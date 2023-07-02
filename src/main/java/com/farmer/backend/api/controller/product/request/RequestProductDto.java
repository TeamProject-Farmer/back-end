package com.farmer.backend.api.controller.product.request;

import com.farmer.backend.domain.product.Product;
import com.farmer.backend.domain.product.productcategory.ProductCategory;
import com.farmer.backend.domain.product.ProductSize;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class RequestProductDto {

    private Long id;
    @NotNull
    private ProductCategory category;
    @NotBlank
    private String name;
    @NotNull
    private Integer stockQuantity;
    @NotNull
    private Integer price;

    private Integer sellQuantity;
    private Integer discountRate;
    @NotNull
    private String thumbnailImg;
    @NotNull
    private ProductSize size;
    private String description;
    private String detailImg1;
    private String detailImg2;
    private String detailImg3;
    private String detailImg4;
    private String detailImg5;

    public Product toEntity() {
        return Product.builder()
                .id(id)
                .category(category)
                .name(name)
                .stockQuantity(stockQuantity)
                .price(price)
                .discountRate(discountRate)
                .thumbnailImg(thumbnailImg)
                .size(size)
                .description(description)
                .detailImg1(detailImg1)
                .detailImg2(detailImg2)
                .detailImg3(detailImg3)
                .detailImg4(detailImg4)
                .detailImg5(detailImg5)
                .build();
    }

}
