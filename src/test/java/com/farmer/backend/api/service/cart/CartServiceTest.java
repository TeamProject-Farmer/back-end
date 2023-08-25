package com.farmer.backend.api.service.cart;

import com.farmer.backend.api.controller.cart.request.RequestCartProductQuantityDto;
import com.farmer.backend.api.controller.cart.request.RequestProductCartDto;
import com.farmer.backend.api.controller.cart.response.ResponseCartProductListDto;
import com.farmer.backend.api.controller.cart.response.ResponseCartProductQuantityDto;
import com.farmer.backend.domain.cart.Cart;
import com.farmer.backend.domain.cart.CartQueryRepositoryImpl;
import com.farmer.backend.domain.cart.CartRepository;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.domain.options.Options;
import com.farmer.backend.domain.product.Product;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
class CartServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartQueryRepositoryImpl cartQueryRepositoryImpl;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("장바구니 추가")
    void addToCart() {
        Cart savedCart = null;
        for (int i = 0; i < 40; i++) {
            Product product = em.find(Product.class, Long.valueOf(i));
            Options options = em.find(Options.class, 1L);
            RequestProductCartDto requestProductCartDto = new RequestProductCartDto(product, options, 3);
            Member member = memberRepository.findByEmail("kce2360@naver.com").orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));
            savedCart = cartRepository.save(requestProductCartDto.toEntity(member));
        }
        assertThat(savedCart).isNotNull();
    }

    @Test
    @DisplayName("장바구니 목록")
    void cartList() {
        Member member = memberRepository.findByEmail("codms7020@naver.com").orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        List<ResponseCartProductListDto> cartProductListByMember = cartQueryRepositoryImpl.findCartProductListByMember(member);
        for (ResponseCartProductListDto responseCartProductListDto : cartProductListByMember) {
            log.info(responseCartProductListDto.getProductName());
        }
        assertThat(cartProductListByMember.size()).isNotZero();
    }

    @Test
    @DisplayName("장바구니 상품 수량 변경")
    void changeQuantityAction() {
        RequestCartProductQuantityDto requestCartProductQuantityDto = new RequestCartProductQuantityDto(2429L, "plus");
        Member member = memberRepository.findByEmail("codms7020@naver.com").orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Cart findCartProduct = cartRepository.findById(requestCartProductQuantityDto.getCartId()).orElseThrow(() -> new CustomException(ErrorCode.CART_PRODUCT_NOT_FOUNT));
        Integer originCount = findCartProduct.getCount();
        Integer beforeCount = findCartProduct.getCount();
        if (requestCartProductQuantityDto.getQuantityCond().equals("plus")) {
            beforeCount++;
        } else {
            beforeCount--;
        }
        findCartProduct.cartProductQuantityUpdate(beforeCount);
        ResponseCartProductQuantityDto cartProductByCartId = cartQueryRepositoryImpl.findCartProductByCartId(findCartProduct.getId());
        log.info("cartProductByCartId={}", originCount);
        assertThat(cartProductByCartId.getCount()).isEqualTo(originCount + 1);
    }

}