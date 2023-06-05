package com.farmer.backend.repository.admin.orders;

import com.farmer.backend.dto.admin.orders.ResponseOrdersDto;
import com.farmer.backend.dto.admin.orders.SearchOrdersCondition;
import com.farmer.backend.entity.*;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.farmer.backend.entity.QMember.member;
import static com.farmer.backend.entity.QOrders.orders;

@Repository
public class OrderQueryRepositoryImpl implements OrderQueryRepository {

    private final JPAQueryFactory query;

    public OrderQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }


    @Override
    public List<ResponseOrdersDto> findAll(Pageable pageable, SearchOrdersCondition ordersCondition, String orderCond) {

        return query
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
                .where(likeOrderNumber(ordersCondition.getOrderNumber()),
                        likeMemberName(ordersCondition.getMemberName()),
                        likeEmail(ordersCondition.getEmail()),
                        orders.orderStatus.ne(OrderStatus.WAIT),
                        orders.orderStatus.ne(OrderStatus.REFUND))
                .join(orders.member, member)
                .fetch();

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

}
