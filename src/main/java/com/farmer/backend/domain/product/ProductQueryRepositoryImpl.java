package com.farmer.backend.domain.product;

import com.farmer.backend.api.controller.user.product.response.ResponseProductDtoList;
import com.farmer.backend.api.controller.user.product.response.ResponseReviewAndQnaDto;
import com.farmer.backend.api.controller.user.product.response.ResponseShopBySizeProduct;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.farmer.backend.domain.admin.qna.QQna.qna;
import static com.farmer.backend.domain.product.QProduct.product;
import static com.farmer.backend.domain.product.productcategory.QProductCategory.productCategory;
import static com.farmer.backend.domain.product.productreview.QProductReviews.productReviews;


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

    @Override
    public Page<ResponseProductDtoList> productList(Long categoryId, Pageable pageable, ProductOrderCondition orderCondition) {
        List<ResponseProductDtoList> productList = query
                .select(Projections.constructor(ResponseProductDtoList.class,
                        product.id,
                        product.name,
                        product.thumbnailImg,
                        product.discountRate,
                        product.price,
                        product.averageStarRating,
                        product.reviewCount))
                .from(product)
                .where(product.category.id.eq(categoryId))
                .orderBy(productOrderCondition(orderCondition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query
                .select(product.count())
                .from(product)
                .where(product.category.id.eq(categoryId))
                .fetchOne();

        return new PageImpl<>(productList, pageable, count);
    }

    @Override
    public List<ResponseProductDtoList> eventProductList(ProductDivision division) {
        List<ResponseProductDtoList> productList = query
                .select(Projections.constructor(ResponseProductDtoList.class,
                        product.id,
                        product.name,
                        product.thumbnailImg,
                        product.discountRate,
                        product.price,
                        product.averageStarRating,
                        product.reviewCount))
                .from(product)
                .orderBy(product.createdDate.desc())
                .where(product.division.eq(division))
                .fetch();
        return productList;
    }

    @Override
    public List<ResponseProductDtoList> bestProductList() {
        List<ResponseProductDtoList> productList = query
                .select(Projections.constructor(ResponseProductDtoList.class,
                        product.id,
                        product.name,
                        product.thumbnailImg,
                        product.discountRate,
                        product.price,
                        product.averageStarRating,
                        product.reviewCount))
                .from(product)
                .limit(10)
                .orderBy(product.averageStarRating.desc())
                .fetch();
        return productList;
    }

    @Override
    public ResponseShopBySizeProduct findByShopBySizeProductOne(ProductSize size) {
        ResponseShopBySizeProduct shopBySizeProduct = query
                .select(Projections.constructor(ResponseShopBySizeProduct.class,
                        product.thumbnailImg,
                        product.size,
                        productCategory.id))
                .from(product)
                .join(product.category, productCategory)
                .where(product.size.eq(size))
                .fetchFirst();
        return shopBySizeProduct;
    }

    @Override
    public Page<ResponseProductDtoList> findByShopBySizeProductList(ProductSize size, Pageable pageable, ProductOrderCondition orderCondition) {
        List<ResponseProductDtoList> productList = query
                .select(Projections.constructor(ResponseProductDtoList.class,
                        product.id,
                        product.name,
                        product.thumbnailImg,
                        product.discountRate,
                        product.price,
                        product.averageStarRating,
                        product.reviewCount))
                .from(product)
                .where(product.size.eq(size))
                .orderBy(productOrderCondition(orderCondition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query
                .select(product.count())
                .from(product)
                .where(product.size.eq(size))
                .fetchOne();

        return new PageImpl<>(productList, pageable, count);
    }

    @Override
    public ResponseReviewAndQnaDto reviewAndQnaInfo(Long productId, Double reviewAverage){

        System.out.println(reviewAverage);

        Long reviewCount=query
                .select(productReviews.count())
                .from(productReviews)
                .where(productReviews.orderProduct.product.id.eq(productId))
                .fetchOne();

        System.out.println(reviewCount);

        Long qnaCount=query
                .select(qna.count())
                .from(qna)
                .where(qna.product.id.eq(productId))
                .fetchOne();

        System.out.println(qnaCount);

        return new ResponseReviewAndQnaDto(productId,reviewCount, qnaCount,reviewAverage);

    }
    private OrderSpecifier<?> productOrderCondition(ProductOrderCondition orderCondition) {
        Order order = Order.DESC;

        if (Objects.isNull(orderCondition) || orderCondition.equals(ProductOrderCondition.NEWS)) {
            return new OrderSpecifier<>(order, product.createdDate);
        } else if (orderCondition.equals(ProductOrderCondition.REVIEW)) {
            return new OrderSpecifier<>(order.DESC, product.reviewCount);
        } else if (orderCondition.equals(ProductOrderCondition.HIGH)) {
            return new OrderSpecifier<>(order.DESC, product.price);
        } else if (orderCondition.equals(ProductOrderCondition.LOW)) {
            return new OrderSpecifier<>(order.ASC, product.price);
        }

        return new OrderSpecifier(Order.DESC, NullExpression.DEFAULT, OrderSpecifier.NullHandling.Default);
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
