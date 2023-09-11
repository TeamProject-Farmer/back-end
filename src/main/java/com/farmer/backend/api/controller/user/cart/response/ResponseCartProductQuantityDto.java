package com.farmer.backend.api.controller.user.cart.response;

import com.farmer.backend.domain.cart.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseCartProductQuantityDto {

    private Long productId;
    private Integer count;
    private Integer productPrice;
    private Integer totalPrice;


}
