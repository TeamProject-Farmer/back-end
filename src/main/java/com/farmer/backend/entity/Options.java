package com.farmer.backend.entity;

import com.farmer.backend.dto.admin.product.RequestOptionDto;
import com.farmer.backend.dto.admin.product.RequestProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 100)
    private String optionName;
    private Integer optionPrice;

//    public void optionUpdate(RequestProductDto productDto) {
//        this.id = productDto.getOptionId();
//        this.product = productDto.getProduct();
//        this.optionName = productDto.getOptionName();
//        this.optionPrice = productDto.getOptionPrice();
//    }

}
