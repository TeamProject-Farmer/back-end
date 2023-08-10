package com.farmer.backend.api.service.order;

import com.farmer.backend.api.controller.order.request.RequestOrderInfoDto;
import com.farmer.backend.api.controller.order.request.SearchOrdersCondition;
import com.farmer.backend.api.controller.order.response.ResponseOrderDetailDto;
import com.farmer.backend.api.controller.order.response.ResponseOrderInfoDto;
import com.farmer.backend.api.controller.order.response.ResponseOrdersAndPaymentDto;
import com.farmer.backend.api.controller.order.response.ResponseOrdersDto;
import com.farmer.backend.api.controller.orderproduct.response.ResponseOrderProductListDto;
import com.farmer.backend.domain.delivery.Delivery;
import com.farmer.backend.domain.delivery.DeliveryRepository;
import com.farmer.backend.domain.deliveryaddress.DeliveryAddress;
import com.farmer.backend.domain.deliveryaddress.DeliveryAddressQueryRepository;
import com.farmer.backend.domain.deliveryaddress.DeliveryAddressRepository;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.domain.orders.Orders;
import com.farmer.backend.domain.product.ProductQueryRepository;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.domain.orderproduct.OrderProductQueryRepository;
import com.farmer.backend.domain.orderproduct.OrderProductRepository;
import com.farmer.backend.domain.orders.OrderQueryRepository;
import com.farmer.backend.domain.orders.OrderRepository;
import com.farmer.backend.domain.payment.PaymentQueryRepository;
import com.farmer.backend.domain.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepositoryImpl;
    private final OrderProductQueryRepository orderProductQueryRepositoryImpl;
    private final DeliveryRepository deliveryRepository;
    private final MemberRepository memberRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final EntityManager em;

    /**
     * 주문 전체 리스트
     * @param pageable 페이징
     * @param searchCondition 주문 정보 검색
     * @param sortOrderCond 주문 상태 정렬
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ResponseOrdersDto> orderList(Pageable pageable, SearchOrdersCondition searchCondition, String sortOrderCond) {
        if (Objects.isNull(sortOrderCond)) {
            return orderQueryRepositoryImpl.findAll(pageable, searchCondition);
        } else if (sortOrderCond.toString().equals("ORDER")) {
            return orderQueryRepositoryImpl.findOrderList(pageable, searchCondition, sortOrderCond);
        }
        return orderQueryRepositoryImpl.findOrderStatusList(pageable, searchCondition, sortOrderCond);
    }

    /**
     * 주문 상세 조회
     * @param orderId 주문 일련번호
     * @return
     */
    @Transactional(readOnly = true)
    public List<Object> orderInfo(Long orderId) {
        List<Object> orderDetailContent = new ArrayList<>();
        Map<String, List<ResponseOrdersAndPaymentDto>> ordersAndPaymentMap = new HashMap<>();
        Map<String, List<ResponseOrderDetailDto>> orderDetailMap = new HashMap<>();
        List<ResponseOrdersAndPaymentDto> ordersAndPayment = orderQueryRepositoryImpl.findOrdersAndPayment(orderId);
        List<ResponseOrderDetailDto> orderDetail = orderProductQueryRepositoryImpl.findOrderDetail(orderId);

        ordersAndPaymentMap.put("주문 결제정보", ordersAndPayment);
        orderDetailMap.put("주문상품 상세정보", orderDetail);
        orderDetailContent.add(ordersAndPaymentMap);
        orderDetailContent.add(orderDetailMap);

        return orderDetailContent;
    }
    @Transactional
    public void orderStatusUpdate(Long orderId, String orderStatus) {
        orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND)).orderStatusUpdateAction(orderStatus);
    }

    /**
     * 주문 삭제
     * @param orderId 주문 일련번호
     * @return
     */
    @Transactional
    public void orderRemove(Long orderId) {
        Orders findOrder = orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        orderRepository.delete(findOrder);
    }

    /**
     * 주문자 배송지 정보 조회
     * @param userId 회원 아이디
     * @return
     */
    @Transactional(readOnly = true)
    public ResponseOrderInfoDto deliveryAddressForm(String userId) {
        Member findMember = memberRepository.findByEmail(userId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByMember(findMember);
        if (deliveryAddress != null) {
            return ResponseOrderInfoDto.builder()
                    .username(deliveryAddress.getName())
                    .zipcode(deliveryAddress.getZipcode())
                    .address(deliveryAddress.getAddress())
                    .addressDetail(deliveryAddress.getAddressDetail())
                    .zipcode(deliveryAddress.getZipcode())
                    .phoneNumber(deliveryAddress.getHp())
                    .build();
        }
        return null;
    }

    /**
     * 주문 생성
     * @param orderInfoDto 주문/결제 정보
     * @return Long
     */
    @Transactional
    public Long createOrder(RequestOrderInfoDto orderInfoDto) {
        Delivery savedDelivery = deliveryRepository.save(orderInfoDto.toEntityDelivery());
        Member findMember = memberRepository.findByNickname(orderInfoDto.getUsername()).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        if (orderInfoDto.isDefaultAddr()) {
            DeliveryAddress oldAddress = deliveryAddressRepository.findByMember(findMember);
            if (Objects.isNull(oldAddress)) {
                deliveryAddressRepository.save(orderInfoDto.toEntityDeliveryAddress(findMember));
            } else {
                oldAddress.updateDeliveryAddress(oldAddress);
            }
        }
        findMember.deductionPoint(findMember, orderInfoDto);

        Orders createdOrder = orderRepository.save(orderInfoDto.toEntity(findMember, savedDelivery));

        return createdOrder.getId();
    }
}
