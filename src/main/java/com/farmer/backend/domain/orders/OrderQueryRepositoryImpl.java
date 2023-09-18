package com.farmer.backend.domain.orders;

import com.farmer.backend.api.controller.admin.order.response.ResponseOrderDetailDto;
import com.farmer.backend.api.controller.admin.order.response.ResponseOrderListDto;
import com.farmer.backend.api.controller.admin.orderproduct.response.ResponseOrderProductDto;
import com.farmer.backend.api.controller.user.options.response.ResponseOptionDto;
import com.farmer.backend.api.controller.user.order.request.SearchOrdersCondition;
import com.farmer.backend.api.controller.user.order.response.ResponseOrdersAndPaymentDto;
import com.farmer.backend.api.controller.user.order.response.ResponseOrdersDto;
import com.farmer.backend.domain.delivery.QDelivery;
import com.farmer.backend.domain.deliveryaddress.QDeliveryAddress;
import com.farmer.backend.domain.orderproduct.QOrderProduct;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.farmer.backend.domain.delivery.QDelivery.delivery;
import static com.farmer.backend.domain.deliveryaddress.QDeliveryAddress.deliveryAddress;
import static com.farmer.backend.domain.member.QMember.member;
import static com.farmer.backend.domain.orderproduct.QOrderProduct.orderProduct;
import static com.farmer.backend.domain.orders.QOrders.orders;
import static com.farmer.backend.domain.payment.QPayment.payment;

@Repository
public class OrderQueryRepositoryImpl implements OrderQueryRepository {

    private final JPAQueryFactory query;

    public OrderQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<ResponseOrderListDto> findOrderList(SearchOrdersCondition searchCond) {
        return query
                .select(Projections.constructor(
                        ResponseOrderListDto.class,
                        orders.id,
                        orders.createdDate,
                        orders.orderNumber,
                        orders.member.nickname,
                        Projections.list(
                                Projections.constructor(
                                        ResponseOrderProductDto.class,
                                        orderProduct.product.name,
                                        orderProduct.product.thumbnailImg,
                                        Projections.list(
                                                Projections.constructor(
                                                        ResponseOptionDto.class,
                                                        orderProduct.options.id,
                                                        orderProduct.options.optionName,
                                                        orderProduct.options.optionPrice
                                                )
                                        )
                                )
                        ),
                        orders.orderPrice,
                        orders.totalQuantity,
                        orders.orderStatus,
                        orders.payMethod
                ))
                .from(orders)
                .join(orderProduct).on(orderProduct.orders.eq(orders))
                .leftJoin(orderProduct.options)
                .leftJoin(orders.member)
                .where(
                        likeOrderNumber(searchCond.getOrderNumber()), likeOrderName(searchCond.getOrderName())
                )
                .orderBy(orders.createdDate.desc())
                .fetch();
    }

    @Override
    public List<ResponseOrdersAndPaymentDto> findOrdersAndPayment(Long orderId) {
        List<ResponseOrdersAndPaymentDto> fetch = query
                .select(Projections.constructor(ResponseOrdersAndPaymentDto.class,
                        orders.id,
                        orders.orderNumber,
                        orders.member.email,
                        orders.delivery.address,
                        orders.delivery.deliveryStatus,
                        orders.delivery.memo,
                        orders.orderPrice,
                        payment.discountPrice,
                        payment.deliveryPrice,
                        payment.usePointPrice,
                        payment.addPoint,
                        orders.payMethod,
                        payment.finalAmount
                        )
                )
                .from(payment)
                .join(payment.orders, orders)
                .where(orders.id.eq(orderId))
                .fetch();
        return fetch;
    }

    @Override
    public ResponseOrderDetailDto findByOrderDetail(Long orderId) {
        return query
                .select(Projections.constructor(
                        ResponseOrderDetailDto.class,
                        orders.orderNumber,
                        orders.createdDate,
                        orders.member.nickname,
                        deliveryAddress.hp,
                        orders.delivery.address,
                        orders.member.email
                ))
                .from(orders)
                .leftJoin(deliveryAddress).on(deliveryAddress.member.eq(orders.member))
                .where(orders.id.eq(orderId))
                .fetchOne();
    }

    private BooleanExpression likeOrderNumber(String orderNumber) {
        return orderNumber != null ? orders.orderNumber.contains(orderNumber) : null;
    }

    private BooleanExpression likeOrderName(String orderName) {
        return orderName != null ? orders.member.nickname.contains(orderName) : null;
    }


    private BooleanExpression likeEmail(String email) {
        return email != null ? orders.member.email.contains(email) : null;
    }

    private BooleanExpression eqOrderStatus(String statusName) {
        return statusName != null ? orders.orderStatus.eq(OrderStatus.valueOf(statusName)) : null;
    }



}
