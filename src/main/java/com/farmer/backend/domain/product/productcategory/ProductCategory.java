package com.farmer.backend.domain.product.productcategory;

import com.farmer.backend.domain.BaseTimeEntity;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProductCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pc_id")
    private Long id;

    @NotNull
    @Column(length = 20)
    private String name;

    @Column(length = 512)
    private String imgUrl;

    @Column(length = 1)
    @NotNull
    private char useYn;

    private Integer sortOrder;
}
