package com.farmer.backend.api.service.cart;

import com.farmer.backend.api.controller.cart.request.RequestCartProductQuantityDto;
import com.farmer.backend.api.controller.cart.request.RequestProductCartDto;
import com.farmer.backend.api.controller.cart.response.ResponseCartProductListDto;
import com.farmer.backend.api.controller.cart.response.ResponseCartProductQuantityDto;
import com.farmer.backend.domain.cart.Cart;
import com.farmer.backend.domain.cart.CartQueryRepository;
import com.farmer.backend.domain.cart.CartRepository;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartQueryRepository cartQueryRepositoryImpl;
    private final MemberRepository memberRepository;

    /**
     * 장바구니에 상품 등록
     * @param productCartDto 상품 정보
     * @param memberEmail 회원 아이디
     */
    @Transactional
    public void addToCart(RequestProductCartDto productCartDto, String memberEmail) {
        Member findMember = memberRepository.findByEmail(memberEmail).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        cartRepository.save(productCartDto.toEntity(findMember));
    }

    /**
     * 장바구니 목록
     * @param username 회원 아이디
     * @return
     */
    @Transactional
    public List<ResponseCartProductListDto> cartList(String username) {
        Member findMember = memberRepository.findByEmail(username).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return cartQueryRepositoryImpl.findCartProductListByMember(findMember);
    }

    /**
     * 장바구니 상품 수량 변경
     * @param cartProductQuantityDto 장바구니 수량정보
     * @param username 회원 아이디
     * @return
     */
    @Transactional
    public ResponseCartProductQuantityDto changeQuantityAction(RequestCartProductQuantityDto cartProductQuantityDto, String username) {
        Member findMember = memberRepository.findByEmail(username).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Cart findCartProduct = cartRepository.findById(cartProductQuantityDto.getCartId()).orElseThrow(() -> new CustomException(ErrorCode.CART_PRODUCT_NOT_FOUNT));
        Integer beforeCount = findCartProduct.getCount();
        if (cartProductQuantityDto.getQuantityCond().equals("plus")) {
            beforeCount++;
        } else {
            beforeCount--;
        }
        findCartProduct.cartProductQuantityUpdate(beforeCount);
        return cartQueryRepositoryImpl.findCartProductByCartId(findCartProduct.getId());
    }

    @Transactional
    public void removeToCartProduct(Long[] cartId) {
        for (Long id : cartId) {
            Cart findCartProduct = cartRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.CART_PRODUCT_NOT_FOUNT));
            cartRepository.delete(findCartProduct);
        }
    }
}
