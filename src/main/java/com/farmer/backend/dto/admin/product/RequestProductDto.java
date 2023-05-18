package com.farmer.backend.dto.admin.product;

import com.farmer.backend.entity.Product;
import com.farmer.backend.entity.ProductCategory;
import com.farmer.backend.entity.ProductSize;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @NotNull
    private Integer sellQuantity;
    private Integer discountRate;
    @NotNull
    private String thumbnailImg;
    private String brandName;
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
                .brandName(brandName)
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
