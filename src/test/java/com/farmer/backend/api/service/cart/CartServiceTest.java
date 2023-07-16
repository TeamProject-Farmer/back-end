package com.farmer.backend.api.service.cart;

import com.farmer.backend.api.controller.cart.request.RequestProductCartDto;
import com.farmer.backend.domain.cart.CartRepository;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.domain.product.Product;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class CartServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CartRepository cartRepository;

    @Test
    @DisplayName("장바구니 추가")
    void addToCart() {

    }

}