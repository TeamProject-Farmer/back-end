package com.farmer.backend.api.service.order;

import com.farmer.backend.api.controller.admin.order.response.ResponseOrderDetailDto;
import com.farmer.backend.api.controller.admin.order.response.ResponseOrderListDto;
import com.farmer.backend.api.controller.user.order.request.RequestOrderInfoDto;
import com.farmer.backend.api.controller.user.order.request.RequestOrderProductDto;
import com.farmer.backend.api.controller.user.order.request.SearchOrdersCondition;
import com.farmer.backend.api.controller.user.order.response.*;
import com.farmer.backend.domain.delivery.Delivery;
import com.farmer.backend.domain.delivery.DeliveryRepository;
import com.farmer.backend.domain.deliveryaddress.DeliveryAddress;
import com.farmer.backend.domain.deliveryaddress.DeliveryAddressRepository;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.domain.options.OptionRepository;
import com.farmer.backend.domain.options.Options;
import com.farmer.backend.domain.orders.Orders;
import com.farmer.backend.domain.payment.Payment;
import com.farmer.backend.domain.product.Product;
import com.farmer.backend.domain.product.ProductRepository;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.domain.orderproduct.OrderProductQueryRepository;
import com.farmer.backend.domain.orderproduct.OrderProductRepository;
import com.farmer.backend.domain.orders.OrderQueryRepository;
import com.farmer.backend.domain.orders.OrderRepository;
import com.farmer.backend.domain.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepositoryImpl;
    private final OrderProductRepository orderProductRepository;
    private final OrderProductQueryRepository orderProductQueryRepositoryImpl;
    private final ProductRepository productRepository;
    private final DeliveryRepository deliveryRepository;
    private final MemberRepository memberRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final OptionRepository optionRepository;
    private final PaymentRepository paymentRepository;

    /**
     * 주문 상세 조회
     * @param orderId 주문 일련번호
     * @return
     */
    @Transactional(readOnly = true)
    public ResponseOrderDetailDto orderDetail(Long orderId) {
        return orderQueryRepositoryImpl.findByOrderDetail(orderId);
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
     * @return ResponseOrderCompleteDto
     */
    @Transactional
    public ResponseOrderCompleteDto createOrder(RequestOrderInfoDto orderInfoDto, String memberEmail) {

        Delivery savedDelivery = deliveryRepository.save(orderInfoDto.toEntityDelivery());
        Member findMember = memberRepository.findByEmail(memberEmail).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        DeliveryAddress address = orderInfoDto.toEntityDeliveryAddress(findMember);

        if (orderInfoDto.isDefaultAddr()) {
            DeliveryAddress oldAddress = deliveryAddressRepository.findByMember(findMember);
            if (Objects.isNull(oldAddress)) {
                deliveryAddressRepository.save(address);
            } else {
                oldAddress.updateDeliveryAddress(address);
            }
        }
        int totalCount = orderInfoDto.getOrderProduct().stream().map(orderInfo -> orderInfo.getCount().intValue()).mapToInt(cnt -> cnt).sum();
        findMember.deductionPoint(findMember, orderInfoDto);

        Orders createdOrder = orderRepository.save(orderInfoDto.toEntity(findMember, savedDelivery, totalCount));

        List<RequestOrderProductDto> orderProduct = orderInfoDto.getOrderProduct().stream().collect(Collectors.toList());
        for (RequestOrderProductDto requestOrderProductDto : orderProduct) {
            Product product = productRepository.findById(requestOrderProductDto.getProductId()).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
            if (requestOrderProductDto.getOptionId() == null) {
                orderProductRepository.save(orderInfoDto.toEntityOrderProduct(product, null, requestOrderProductDto.getCount(), requestOrderProductDto.getOrderPrice(), createdOrder));
            } else {
                Options options = optionRepository.findById(requestOrderProductDto.getOptionId()).orElseThrow(() -> new CustomException(ErrorCode.OPTION_NOT_FOUND));
                orderProductRepository.save(orderInfoDto.toEntityOrderProduct(product, options, requestOrderProductDto.getCount(), requestOrderProductDto.getOrderPrice(), createdOrder));
            }
        }
        paymentRepository.save(Payment.toEntity(createdOrder, orderInfoDto));
        return ResponseOrderCompleteDto.orderCompleteData(findMember, savedDelivery, orderInfoDto, createdOrder);
    }

    /**
     * 주문 조회(주문 번호)
     * @param orderNumber 주문번호
     * @return ResponseOrderCompleteDto
     */
    @Transactional
    public ResponseOrderCompleteDto orderCheck(String orderNumber) {
        Orders findOrders = orderRepository.findByOrderNumber(orderNumber).orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        DeliveryAddress memberInfo = deliveryAddressRepository.findByMember(findOrders.getMember());
        return ResponseOrderCompleteDto.orderCompleteData(findOrders, memberInfo);
    }

    /**
     * =========================================================================================================
     * Admin logic
     */

    /**
     * 주문 전체 조회
     * @param searchOrdersCondition 검색 정보
     * @return
     */
    @Transactional
    public List<ResponseOrderListDto> orderList(SearchOrdersCondition searchOrdersCondition) {
        return orderRepository.findOrderList(searchOrdersCondition);
    }
}
