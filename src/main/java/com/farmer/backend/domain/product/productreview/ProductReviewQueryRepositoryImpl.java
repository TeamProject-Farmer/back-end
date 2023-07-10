package com.farmer.backend.domain.product.productreview;

import com.farmer.backend.api.controller.review.response.ResponseProductReviewListDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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
    public List<ResponseProductReviewListDto> bestReviewList(){
        List<ResponseProductReviewListDto> productReviewList = query
                .select(Projections.constructor(
                        ResponseProductReviewListDto.class,
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


}
