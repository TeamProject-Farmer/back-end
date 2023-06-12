package com.farmer.backend.repository.admin.orders;

import com.farmer.backend.dto.admin.orders.*;
import com.farmer.backend.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.function.Supplier;

import static com.farmer.backend.entity.QMember.member;
import static com.farmer.backend.entity.QOrders.orders;

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
    public List<String> findOrderDetail(Long orderId) {
        return null;
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
