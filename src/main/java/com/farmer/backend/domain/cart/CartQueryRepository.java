package com.farmer.backend.domain.cart;

import com.farmer.backend.api.controller.cart.response.ResponseCartProductListDto;
import com.farmer.backend.domain.member.Member;

import java.util.List;

public interface CartQueryRepository {
    List<ResponseCartProductListDto> findCartProductListByMember(Member findMember);
}
