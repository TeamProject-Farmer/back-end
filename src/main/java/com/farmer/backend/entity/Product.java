package com.farmer.backend.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @NotNull
    private Integer sellQuantity;

    private Integer discountRate;

    @Column(length = 255)
    private String thumbnailImg;

    @Column(length = 40)
    private String brandName;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ProductSize size;

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

}
