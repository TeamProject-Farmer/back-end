package com.farmer.backend.domain.product;

import com.farmer.backend.domain.BaseTimeEntity;
import com.farmer.backend.api.controller.user.product.request.RequestProductDto;
import com.farmer.backend.domain.product.productcategory.ProductCategory;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter
@Builder
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pc_id")
    private ProductCategory category;

    @NotNull
    @Column(length = 100)
    private String name;

    @NotNull
    private Integer stockQuantity;

    @NotNull
    private Integer price;

    private Integer sellQuantity;

    @ColumnDefault("0.0")
    private double averageStarRating;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ProductDivision division;

    private Integer discountRate;

    @Column(length = 255)
    private String thumbnailImg;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ProductSize size;

    @ColumnDefault("0")
    private Long reviewCount;

    @Column(columnDefinition = "text")
    private String description;

    @Column(length = 255)
    private String detailImg1;
    @Column(length = 255)
    private String detailImg2;
    @Column(length = 255)
    private String detailImg3;
    @Column(length = 255)
    private String detailImg4;
    @Column(length = 255)
    private String detailImg5;

    public void productUpdate(RequestProductDto productDto) {
        this.category = productDto.getCategory();
        this.name = productDto.getName();
        this.stockQuantity = productDto.getStockQuantity();
        this.price = productDto.getPrice();
        this.sellQuantity = productDto.getSellQuantity();
        this.discountRate = productDto.getDiscountRate();
        this.thumbnailImg = productDto.getThumbnailImg();
        this.size = productDto.getSize();
        this.description = productDto.getDescription();
        this.detailImg1 = productDto.getDetailImg1();
        this.detailImg2 = productDto.getDetailImg2();
        this.detailImg3 = productDto.getDetailImg3();
        this.detailImg4 = productDto.getDetailImg4();
        this.detailImg5 = productDto.getDetailImg5();
    }

    public void updateAverageStar(double averageStarRating ){
        this.averageStarRating=averageStarRating;
    }

    public void updateReviewCount(){
        this.reviewCount+=1;
    }

}
