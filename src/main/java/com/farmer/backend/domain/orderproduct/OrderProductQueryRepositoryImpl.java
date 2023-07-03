package com.farmer.backend.domain.orderproduct;

import com.farmer.backend.api.controller.order.response.ResponseOrderDetailDto;
import com.farmer.backend.api.controller.orderproduct.request.RequestOrderProductStatusSearchDto;
import com.farmer.backend.api.controller.orderproduct.response.ResponseOrderProductDetailDto;
import com.farmer.backend.domain.orders.OrderStatus;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.farmer.backend.domain.orderproduct.QOrderProduct.orderProduct;
import static com.farmer.backend.domain.orders.QOrders.orders;


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

    @Override
    public List<ResponseOrderProductDetailDto> findUserOrderProductDetail(RequestOrderProductStatusSearchDto statusSearchDto, Long orderId) {
        List<ResponseOrderProductDetailDto> productOrderList = query
                .select(Projections.constructor(
                                ResponseOrderProductDetailDto.class,
                                orders.orderNumber,
                                orderProduct.product.thumbnailImg,
                                orderProduct.product.id,
                                orderProduct.product.name,
                                orderProduct.count,
                                orderProduct.options.optionName,
                                orderProduct.options.optionPrice,
                                orderProduct.orderPrice,
                                orders.orderPrice,
                                orders.orderStatus
                        )
                )
                .from(orderProduct)
                .join(orderProduct.orders, orders)
                .where(
                        orderProduct.orders.id.eq(orderId),
                        eqOrderStatus(statusSearchDto.getOrderStatus()),
                        betweenDatetime(statusSearchDto.getStartDate(), statusSearchDto.getEndDate())
                )
                .fetch();
        return productOrderList;
    }

    private BooleanExpression betweenDatetime(LocalDateTime startDate, LocalDateTime endDate) {
        if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
            return null;
        }
        return orders.createdDate.between(startDate, endDate);
    }

    private BooleanExpression eqOrderStatus(OrderStatus orderStatus) {
        return orderStatus != null ? orders.orderStatus.eq(orderStatus) : null;
    }


}
