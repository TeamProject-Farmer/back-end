package com.farmer.backend.repository.admin.orders;

import com.farmer.backend.dto.admin.orders.ResponseOrderDetailDto;
import com.farmer.backend.entity.QOptions;
import com.farmer.backend.entity.QOrderDetail;
import com.farmer.backend.entity.QProduct;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.farmer.backend.entity.QOptions.options;
import static com.farmer.backend.entity.QOrderDetail.orderDetail;
import static com.farmer.backend.entity.QProduct.product;

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
