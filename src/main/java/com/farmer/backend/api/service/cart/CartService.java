package com.farmer.backend.api.service.cart;

import com.farmer.backend.api.controller.cart.request.RequestProductCartDto;
import com.farmer.backend.domain.cart.Cart;
import com.farmer.backend.domain.cart.CartQueryRepository;
import com.farmer.backend.domain.cart.CartRepository;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.login.general.MemberAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartQueryRepository cartQueryRepositoryImpl;
    private final MemberRepository memberRepository;

    @Transactional
    public void addToCart(RequestProductCartDto productCartDto, String memberEmail) {
        Member findMember = memberRepository.findByEmail(memberEmail).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        cartRepository.save(productCartDto.toEntity(findMember));
    }
}
