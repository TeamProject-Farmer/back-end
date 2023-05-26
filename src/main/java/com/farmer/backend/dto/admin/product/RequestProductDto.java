package com.farmer.backend.dto.admin.product;

import com.farmer.backend.entity.Options;
import com.farmer.backend.entity.Product;
import com.farmer.backend.entity.ProductCategory;
import com.farmer.backend.entity.ProductSize;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    private String brandName;
    @NotNull
    private ProductSize size;
    private String description;
    private String detailImg1;
    private String detailImg2;
    private String detailImg3;
    private String detailImg4;
    private String detailImg5;

//    @JsonProperty(value = "optionId")
//    private Long optionId;
//    @JsonProperty(value = "optionName")
//    private String optionName;
//    @JsonProperty(value = "optionPrice")
//    private Integer optionPrice;
//    @JsonProperty(value = "product")
//    private Product product;


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

//    public Options toEntityOptions(Product product) {
//        return Options.builder()
//                .product(product)
//                .optionName(optionName)
//                .optionPrice(optionPrice)
//                .build();
//    }

}
