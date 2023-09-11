package com.farmer.backend.domain.options;

import com.farmer.backend.domain.product.Product;
import com.farmer.backend.api.controller.user.options.request.RequestOptionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 100)
    private String optionName;
    private Integer optionPrice;

    public void optionUpdate(RequestOptionDto optionDto) {
        this.id = optionDto.getId();
        this.product = optionDto.getProduct();
        this.optionName = optionDto.getOptionName();
        this.optionPrice = optionDto.getOptionPrice();
    }

}
