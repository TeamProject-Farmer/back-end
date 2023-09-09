package com.farmer.backend.api.controller.user.cart.request;

import com.farmer.backend.domain.cart.Cart;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.options.Options;
import com.farmer.backend.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RequestProductCartDto {

    private Product product;
    private Options option;
    private Integer count;

    public Cart toEntity(Member member) {
        return Cart.builder()
                .product(product)
                .options(option)
                .member(member)
                .count(count)
                .build();
    }

}
