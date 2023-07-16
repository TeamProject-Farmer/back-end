package com.farmer.backend.api.controller.cart.request;

import com.farmer.backend.domain.cart.Cart;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RequestCartProductQuantityDto {

    private Long cartId;
    private String quantityCond;

    public Cart toEntity() {
        return Cart.builder()
                .id(cartId)
                .build();
    }

}
