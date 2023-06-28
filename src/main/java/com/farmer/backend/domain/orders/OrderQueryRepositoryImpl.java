package com.farmer.backend.domain.orders;

import com.farmer.backend.api.controller.admin.order.request.SearchOrdersCondition;
import com.farmer.backend.api.controller.admin.order.response.ResponseOrdersAndPaymentDto;
import com.farmer.backend.api.controller.admin.order.response.ResponseOrdersDto;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.farmer.backend.domain.member.QMember.member;
import static com.farmer.backend.domain.orders.QOrders.orders;
import static com.farmer.backend.domain.payment.QPayment.payment;

@Repository
public class OrderQueryRepositoryImpl implements OrderQueryRepository {

    private final JPAQueryFactory query;

    public OrderQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<ResponseOrdersDto> findOrderStatusList(Pageable pageable, SearchOrdersCondition searchCond, String sortOrderCond) {
        List<ResponseOrdersDto> orderList = query
                .select(Projections.constructor(ResponseOrdersDto.class,
                        orders.id,
                        orders.member.username,
                        orders.member.email,
                        orders.member.ph,
                        orders.orderStatus,
                        orders.orderPrice,
                        orders.payMethod,
                        orders.totalQuantity,
                        orders.comment,
                        orders.createdDate))
                .from(orders)
                .where(likeOrderNumber(searchCond.getOrderNumber()),
                        likeMemberName(searchCond.getMemberName()),
                        likeEmail(searchCond.getEmail()),
                        eqOrderStatus(sortOrderCond))
                .join(orders.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.id.desc())
                .fetch();

        Long count = query
                .select(orders.count())
                .from(orders)
                .where(likeOrderNumber(searchCond.getOrderNumber()),
                        likeMemberName(searchCond.getMemberName()),
                        likeEmail(searchCond.getEmail()),
                        eqOrderStatus(sortOrderCond)
                )
                .fetchOne();

        return new PageImpl<>(orderList, pageable, count);
    }
    @Override
    public Page<ResponseOrdersDto> findOrderList(Pageable pageable, SearchOrdersCondition searchCond, String sortOrderCond) {
        List<ResponseOrdersDto> orderList = query
                .select(Projections.constructor(ResponseOrdersDto.class,
                        orders.id,
                        orders.member.username,
                        orders.member.email,
                        orders.member.ph,
                        orders.orderStatus,
                        orders.orderPrice,
                        orders.payMethod,
                        orders.totalQuantity,
                        orders.comment,
                        orders.createdDate))
                .from(orders)
                .where(likeOrderNumber(searchCond.getOrderNumber()),
                        likeMemberName(searchCond.getMemberName()),
                        likeEmail(searchCond.getEmail()),
                        orders.orderStatus.ne(OrderStatus.WAIT),
                        orders.orderStatus.ne(OrderStatus.REFUND))
                .join(orders.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.id.desc())
                .fetch();

        Long count = query
                .select(orders.count())
                .from(orders)
                .where(likeOrderNumber(searchCond.getOrderNumber()),
                        likeMemberName(searchCond.getMemberName()),
                        likeEmail(searchCond.getEmail())
                )
                .fetchOne();

        return new PageImpl<>(orderList, pageable, count);
    }

    @Override
    public List<ResponseOrdersAndPaymentDto> findOrdersAndPayment(Long orderId) {
        List<ResponseOrdersAndPaymentDto> fetch = query
                .select(Projections.constructor(ResponseOrdersAndPaymentDto.class,
                        orders.id,
                        orders.orderNumber,
                        orders.member.username,
                        orders.member.ph,
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
    public Page<ResponseOrdersDto> findAll(Pageable pageable, SearchOrdersCondition searchCond) {
        List<ResponseOrdersDto> orderList = query
                .select(Projections.constructor(ResponseOrdersDto.class,
                        orders.id,
                        orders.member.username,
                        orders.member.email,
                        orders.member.ph,
                        orders.orderStatus,
                        orders.orderPrice,
                        orders.payMethod,
                        orders.totalQuantity,
                        orders.comment,
                        orders.createdDate))
                .from(orders)
                .where(likeOrderNumber(searchCond.getOrderNumber()),
                        likeMemberName(searchCond.getMemberName()),
                        likeEmail(searchCond.getEmail())
                )
                .join(orders.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.id.desc())
                .fetch();

        Long count = query
                .select(orders.count())
                .from(orders)
                .where(likeOrderNumber(searchCond.getOrderNumber()),
                        likeMemberName(searchCond.getMemberName()),
                        likeEmail(searchCond.getEmail())
                )
                .fetchOne();

        return new PageImpl<>(orderList, pageable, count);
    }

    private BooleanExpression likeOrderNumber(String orderNumber) {
        return orderNumber != null ? orders.orderNumber.contains(orderNumber) : null;
    }

    private BooleanExpression likeMemberName(String memberName) {
        return memberName != null ? orders.member.username.contains(memberName) : null;
    }

    private BooleanExpression likeEmail(String email) {
        return email != null ? orders.member.email.contains(email) : null;
    }

    private BooleanExpression eqOrderStatus(String statusName) {
        return statusName != null ? orders.orderStatus.eq(OrderStatus.valueOf(statusName)) : null;
    }


//    private BooleanBuilder eqOrderStatus(String statusName) {
//        return nullSafeBuilder(() -> orders.orderStatus.eq(OrderStatus.valueOf(statusName)));
//    }
//
//
//    public static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
//        try {
//            return new BooleanBuilder(f.get());
//        } catch (IllegalArgumentException e) {
//            return new BooleanBuilder();
//        }
//    }


}
