package com.farmer.backend.domain.product.productreview;
import com.farmer.backend.api.controller.review.request.RequestReviewStarDto;
import com.farmer.backend.api.controller.review.response.ResponseBestReviewListDto;
import com.farmer.backend.api.controller.review.response.ResponseProductReviewListDto;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
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

import static com.farmer.backend.domain.orderproduct.QOrderProduct.orderProduct;
import static com.farmer.backend.domain.product.productreview.QProductReviews.productReviews;
import static com.farmer.backend.domain.options.QOptions.options;

@Repository
public class ProductReviewQueryRepositoryImpl implements ProductReviewQueryRepository{

    private final JPAQueryFactory query;

    public ProductReviewQueryRepositoryImpl(EntityManager em){
        this.query=new JPAQueryFactory(em);
    }

    /**
     * 베스트 리뷰 리스트 출력
     */
    @Override
    public List<ResponseBestReviewListDto> bestReviewList(){
        List<ResponseBestReviewListDto> reviewList = query
                .select(Projections.constructor(
                        ResponseBestReviewListDto.class,
                        productReviews.id,
                        productReviews.orderProduct.product.id,
                        productReviews.member.nickname,
                        productReviews.imgUrl,
                        productReviews.content,
                        productReviews.likeCount,
                        productReviews.fiveStarRating

                ))
                .from(productReviews)
                .limit(10L)
                .orderBy(productReviews.likeCount.desc())
                .fetch();
        return reviewList;
    }


    /**
     * 상품별 리뷰 리스트 출력
     */
    @Override
    public Page<ResponseProductReviewListDto> productReviewList(Pageable pageable,
                                                                String sortOrderCond,
                                                                Integer reviewCond,
                                                                Long productId){
        List<ResponseProductReviewListDto> productReviewList = query
                .select(Projections.constructor(
                        ResponseProductReviewListDto.class,
                        productReviews.id,
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
                .leftJoin(options).on(productReviews.orderProduct.options.optionName.eq(options.optionName))
                .where(productReviews.orderProduct.product.id.eq(productId),likeStar(reviewCond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(sortOrderProductReview(sortOrderCond))
                .fetch();

        Long count = query
                .select(productReviews.count())
                .from(productReviews)
                .where(productReviews.orderProduct.product.id.eq(productId),likeStar(reviewCond))
                .orderBy(sortOrderProductReview(sortOrderCond))
                .fetchOne();

        return new PageImpl<>(productReviewList,pageable,count);

    }

    /**
     * 상품별 리뷰 별점 리스트
     */
    @Override
    public RequestReviewStarDto fiveStars(Long productId){

        ArrayList<Long> reviewCount = new ArrayList<>(Arrays.asList(0L, 0L, 0L, 0L, 0L));

        List<Tuple> allStar = query
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

        return new RequestReviewStarDto(starAverage,reviewCount.get(4),reviewCount.get(3),reviewCount.get(2),reviewCount.get(1),reviewCount.get(0));
    }

    /**
     * 상품별 리뷰 이미지
     */

    @Override
    public List<String> productReviewImg(Long productId){
        List<String> imgList = query
                .select(productReviews.imgUrl)
                .from(productReviews)
                .where(productReviews.orderProduct.product.id.eq(productId))
                .orderBy(productReviews.createdDate.desc())
                .fetch();

        return imgList;
    }

    @Override
    public Long productCount(Long productId){
            Long productCount = query
                    .select(orderProduct.product.count())
                    .from(orderProduct)
                    .where(orderProduct.product.id.eq(productId))
                    .fetchOne();
            Long reviewCount = query
                    .select(productReviews.count())
                    .from(productReviews)
                    .where(productReviews.orderProduct.product.id.eq(productId))
                    .fetchOne();


            if(productCount==0){
                throw new CustomException(ErrorCode.PRODUCT_REVIEW_NOT_FOUND);
            } else if (reviewCount==0) {

                throw new CustomException(ErrorCode.PRODUCT_REVIEW_NOT_FOUND);

            }
            return productCount;
    }

    public BooleanExpression likeStar(Integer star){
        return star != null ? productReviews.fiveStarRating.eq(star) :null;
    }

    public OrderSpecifier<?> sortOrderProductReview(String sortOrderCond) {
        if (Objects.isNull(sortOrderCond) || sortOrderCond.equals("recent")) {
            return new OrderSpecifier<>(Order.DESC, productReviews.createdDate);
        } else if (sortOrderCond.equals("best")) {
            return new OrderSpecifier<>(Order.DESC, productReviews.likeCount);
        }
        return new OrderSpecifier(Order.DESC, NullExpression.DEFAULT, OrderSpecifier.NullHandling.Default);
    }
}
