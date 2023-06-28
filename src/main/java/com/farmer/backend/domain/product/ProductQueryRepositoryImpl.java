package com.farmer.backend.domain.product;

import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Objects;

import static com.farmer.backend.domain.product.QProduct.product;


@Repository
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final JPAQueryFactory query;

    public ProductQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Product> findAll(Pageable pageable, String productName, String orderCondition) {
        List<Product> productList = query
                .select(product)
                .from(product)
                .where(likeProductName(productName))
                .orderBy(sortOrderCondition(orderCondition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query
                .select(product.count())
                .from(product)
                .where(likeProductName(productName))
                .fetchOne();

        return new PageImpl<>(productList, pageable, count);

    }

    public BooleanExpression likeProductName(String productName) {
        return productName != null ? product.name.contains(productName) : null;
    }

    public OrderSpecifier<?> sortOrderCondition(String orderCondition) {

        Order order = Order.DESC;

        if (Objects.isNull(orderCondition) || orderCondition.equals("news")) {
            return new OrderSpecifier<>(order, product.createdDate);
        } else if (orderCondition.equals("low")) {
            return new OrderSpecifier<>(order.ASC, product.price);
        } else if (orderCondition.equals("high")) {
            return new OrderSpecifier<>(order, product.price);
        } else if (orderCondition.equals("count")) {
            return new OrderSpecifier<>(order, product.sellQuantity);
        }

        return new OrderSpecifier(Order.DESC, NullExpression.DEFAULT, OrderSpecifier.NullHandling.Default);
    }
}
