package com.farmer.backend.api.service.orderproduct;

import com.farmer.backend.api.controller.orderproduct.request.RequestOrderProductStatusSearchDto;
import com.farmer.backend.api.controller.orderproduct.response.ResponseOrderProductDetailDto;
import com.farmer.backend.api.controller.orderproduct.response.ResponseOrderProductDto;
import com.farmer.backend.api.controller.product.response.ResponseProductDto;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.domain.orderproduct.OrderProduct;
import com.farmer.backend.domain.orderproduct.OrderProductQueryRepository;
import com.farmer.backend.domain.orderproduct.OrderProductRepository;
import com.farmer.backend.domain.orders.OrderRepository;
import com.farmer.backend.domain.orders.Orders;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProductService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderProductQueryRepository orderProductQueryRepositoryImpl;

    /**
     * 마이페이지 -> 구매목록 기능(최대 6까지 데이터 출력)
     * @param userId 회원 아이디(이메일)
     * @return List<ResponseOrderProductDto>
     */
    @Transactional(readOnly = true)
    public List<ResponseOrderProductDto> shoppingList(String userId) {
        Member findMember = memberRepository.findByEmail(userId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        List<Orders> orderList = orderRepository.findByMemberId(findMember.getId());
        List<ResponseOrderProductDto> responseOrderProductDto = new ArrayList<>();

        for (Orders order : orderList) {
            List<OrderProduct> orderProducts = orderProductRepository.findTop6ByOrdersIdOrderByIdDesc(order.getId());
            for (OrderProduct orderProduct : orderProducts) {
                 responseOrderProductDto.add(ResponseOrderProductDto.shoppingList(
                         orderProduct.getProduct().getId(),
                         orderProduct.getProduct().getName(),
                         orderProduct.getProduct().getThumbnailImg()
                 ));
            }
        }
        return responseOrderProductDto;
    }

    /**
     * 마이페이지 장바구니 목록(주문내역 조회)
     * @param statusSearchDto 날짜 정보, 주문 처리상태 검색정보
     * @return List<ResponseOrderProductDetailDto>
     */
    @Transactional(readOnly = true)
    public List<ResponseOrderProductDetailDto> orderList(RequestOrderProductStatusSearchDto statusSearchDto, String userId) {
        Member findMember = memberRepository.findByEmail(userId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        List<Orders> orderList = orderRepository.findByMemberId(findMember.getId());
        List<ResponseOrderProductDetailDto> orderProductList = new ArrayList<>();
        log.info("userId={}", userId);
        for (Orders orders : orderList) {
            orderProductList = orderProductQueryRepositoryImpl.findUserOrderProductDetail(statusSearchDto, orders.getId());
        }

        return orderProductList;
    }
}
