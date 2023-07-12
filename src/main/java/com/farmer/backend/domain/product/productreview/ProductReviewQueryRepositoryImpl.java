package com.farmer.backend.domain.product.productreview;
import com.farmer.backend.api.controller.review.request.RequestReviewStarDto;
import com.farmer.backend.api.controller.review.request.SearchProductReviewCondition;
import com.farmer.backend.api.controller.review.response.ResponseBestReviewListDto;
import com.farmer.backend.api.controller.review.response.ResponseProductReviewListDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

import static com.farmer.backend.domain.product.productreview.QProductReviews.productReviews;

@Repository
public class ProductReviewQueryRepositoryImpl implements ProductReviewQueryRepository{

    private final JPAQueryFactory query;

    public ProductReviewQueryRepositoryImpl(EntityManager em){
        this.query=new JPAQueryFactory(em);
    }

    /**
     * 베스트 리뷰 리스트 출력
     * @return
     */
    @Override
    public List<ResponseBestReviewListDto> bestReviewList(){
        List<ResponseBestReviewListDto> productReviewList = query
                .select(Projections.constructor(
                        ResponseBestReviewListDto.class,
                        productReviews.member.nickname,
                        productReviews.imgUrl,
                        productReviews.content,
                        productReviews.likeCount,
                        productReviews.fiveStarRating

                ))
                .from(productReviews)
                .orderBy(productReviews.likeCount.desc())
                .fetch();
        return productReviewList;
    }

    /**
     * 상품별 리뷰 리스트 출력
     */

    @Override
    public Page<ResponseProductReviewListDto> productReviewList(Pageable pageable,
                                                                String sortOrderCond,
                                                                SearchProductReviewCondition searchCond,
                                                                Long productId){
        List<ResponseProductReviewListDto> productReviewList = query
                .select(Projections.constructor(
                        ResponseProductReviewListDto.class,
                        productReviews.member.nickname,
                        productReviews.fiveStarRating,
                        productReviews.createdDate,
                        productReviews.orderProduct.product.name,
                        productReviews.orderProduct.options.optionName,
                        productReviews.imgUrl,
                        productReviews.likeCount,
                        productReviews.content
                ))
                .from(productReviews)
                .where(productReviews.orderProduct.product.id.eq(productId),likeStar(searchCond.getStar()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(sortOrderProductReview(sortOrderCond))
                .fetch();

        Long count = query
                .select(productReviews.count())
                .from(productReviews)
                .where(productReviews.orderProduct.product.id.eq(productId))
                .fetchOne();

        return new PageImpl<>(productReviewList,pageable,count);

    }

    @Override
    public RequestReviewStarDto fiveStars(Long productId){

        ArrayList<Long> reviewCount = new ArrayList<>(Arrays.asList(0L, 0L, 0L, 0L, 0L));

        List<Tuple> allStar =query
                .select(productReviews.fiveStarRating,productReviews.fiveStarRating.count())
                .from(productReviews)
                .where(productReviews.orderProduct.product.id.eq(productId))
                .groupBy(productReviews.fiveStarRating)
                .fetch();

        for (Tuple reviewStar : allStar){
            Integer reviewStarRating = reviewStar.get(0,Integer.class)-1;
            Long reviewStarCount = reviewStar.get(1,Long.class);

            reviewCount.set(reviewStarRating,reviewStarCount);

        }

        Double starAverage = Double.valueOf(query
                .select((productReviews.fiveStarRating.avg().multiply(10)).round().divide(10.0))
                .from(productReviews)
                .where(productReviews.orderProduct.product.id.eq(productId))
                .fetchOne());

        RequestReviewStarDto reviewStarDto = new RequestReviewStarDto(starAverage,reviewCount.get(4),reviewCount.get(3),reviewCount.get(2),reviewCount.get(1),reviewCount.get(0));
        return reviewStarDto;
    }

    public BooleanExpression likeStar(String star){
        return star != null ? productReviews.fiveStarRating.eq(Integer.valueOf(star)) :null;
    }

    public OrderSpecifier<?> sortOrderProductReview(String sortOrderCond) {
        if (Objects.isNull(sortOrderCond) || sortOrderCond.equals("recent")) {
            return new OrderSpecifier<>(Order.DESC, productReviews.createdDate);
        } else if (sortOrderCond.equals("best")) {
            return new OrderSpecifier<>(Order.ASC, productReviews.likeCount);
        }
        return new OrderSpecifier(Order.DESC, NullExpression.DEFAULT, OrderSpecifier.NullHandling.Default);
    }
}
