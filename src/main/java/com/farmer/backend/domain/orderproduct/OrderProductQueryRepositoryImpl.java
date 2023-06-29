package com.farmer.backend.domain.orderproduct;

import com.farmer.backend.api.controller.admin.order.response.ResponseOrderDetailDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.farmer.backend.domain.orderproduct.QOrderProduct.orderProduct;


@Repository
public class OrderProductQueryRepositoryImpl implements OrderProductQueryRepository {

    private final JPAQueryFactory query;

    public OrderProductQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<ResponseOrderDetailDto> findOrderDetail(Long orderId) {
        List<ResponseOrderDetailDto> orderDetailDto = query
                .select(Projections.constructor(
                                ResponseOrderDetailDto.class,
                                orderProduct.product,
                                orderProduct.count,
                                orderProduct.options.id,
                                orderProduct.options.optionName,
                                orderProduct.options.optionPrice
                        )
                )
                .from(orderProduct)
                .where(orderProduct.orders.id.eq(orderId))
                .fetch();

        return orderDetailDto;
    }

}
