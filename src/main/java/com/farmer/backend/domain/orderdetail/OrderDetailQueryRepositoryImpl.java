package com.farmer.backend.domain.orderdetail;

import com.farmer.backend.api.controller.admin.order.response.ResponseOrderDetailDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.farmer.backend.domain.orderdetail.QOrderDetail.orderDetail;

@Repository
public class OrderDetailQueryRepositoryImpl implements OrderDetailQueryRepository{

    private final JPAQueryFactory query;

    public OrderDetailQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<ResponseOrderDetailDto> findOrderDetail(Long orderId) {
        List<ResponseOrderDetailDto> orderDetailDto = query
                .select(Projections.constructor(
                                ResponseOrderDetailDto.class,
                                orderDetail.product,
                                orderDetail.count,
                                orderDetail.options.id,
                                orderDetail.options.optionName,
                                orderDetail.options.optionPrice
                        )
                )
                .from(orderDetail)
                .where(orderDetail.orders.id.eq(orderId))
                .fetch();

        return orderDetailDto;
    }

}
