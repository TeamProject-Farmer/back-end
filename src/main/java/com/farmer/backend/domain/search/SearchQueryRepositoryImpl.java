package com.farmer.backend.domain.search;

import com.farmer.backend.api.controller.search.response.ResponseSearchProductDto;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

import static com.farmer.backend.domain.product.QProduct.product;
import static com.farmer.backend.domain.product.productreviewstar.QProductReviewAverage.productReviewAverage;

@Repository
public class SearchQueryRepositoryImpl implements SearchQueryRepository {

    private final JPAQueryFactory query;

    public SearchQueryRepositoryImpl(EntityManager em){
        this.query=new JPAQueryFactory(em);
    }

    /**
     * 검색 상품 리스트 출력
     */
    @Override
    public Page<ResponseSearchProductDto> searchProduct(Pageable pageable,
                                                        String sortSearchCond,
                                                        String searchWord){
        List<ResponseSearchProductDto> searchProducts = query
                .select(Projections.constructor(
                        ResponseSearchProductDto.class,
                        product.thumbnailImg,
                        product.name,
                        product.price,
                        product.discountRate,
                        product.reviewCount,
                        ExpressionUtils.as(
                                JPAExpressions.select(productReviewAverage.averageStarRating)
                                        .from(productReviewAverage)
                                        .where(productReviewAverage.product.id.eq(product.id)),"averageStarRating"
                        )
                ))
                .distinct()
                .from(product)
                .where(likeIncludingSearchWord(searchWord).or(likeBelongCategory(searchWord)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(sortOrderSearchProduct(sortSearchCond))

                .fetch();

        Long count = query
                .select(product.count())
                .from(product)
                .where(likeIncludingSearchWord(searchWord))
                .fetchOne();


        return new PageImpl<>(searchProducts,pageable,count);
    }

    private BooleanExpression likeBelongCategory(String searchWord) {

        return searchWord != null ?product.category.name.contains(searchWord):null;


    }

    private BooleanExpression likeIncludingSearchWord(String searchWord) {
        return searchWord != null ? product.name.contains(searchWord):null;

    }

    public OrderSpecifier<?> sortOrderSearchProduct(String sortSearchCond){
        if(Objects.isNull(sortSearchCond)||sortSearchCond.equals("new")){
            return new OrderSpecifier<>(Order.DESC,product.createdDate);
        } else if (sortSearchCond.equals("review")) {
            return new OrderSpecifier<>(Order.DESC,product.reviewCount);
            
        }else if(sortSearchCond.equals("low")){
            return new OrderSpecifier<>(Order.ASC,product.price);
        }else if(sortSearchCond.equals("high")){
            return new OrderSpecifier<>(Order.DESC,product.price);
        }

        return new OrderSpecifier(Order.DESC,NullExpression.DEFAULT,OrderSpecifier.NullHandling.Default);
    }




}
