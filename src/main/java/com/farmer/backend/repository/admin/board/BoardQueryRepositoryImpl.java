package com.farmer.backend.repository.admin.board;

import com.farmer.backend.dto.admin.board.SearchQnaCondition;
import com.farmer.backend.dto.admin.board.SearchReviewCondition;
import com.farmer.backend.entity.Product_reviews;
import com.farmer.backend.entity.Qna;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.farmer.backend.entity.QProduct_reviews.product_reviews;
import static com.farmer.backend.entity.QQna.qna;

@Repository
@Slf4j
public class BoardQueryRepositoryImpl implements BoardQueryRepository {

    private final JPAQueryFactory query;

    public BoardQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    /**
     * Q&A 전체 리스트
     */
    @Override
    public Page<Qna> findAll(Pageable pageable, String sortQnaCond, SearchQnaCondition searchQnaCond) {

        List<Qna> qnaList = query
                .select(qna)
                .from(qna)
                .where(likeQnaId(searchQnaCond.getQnaId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query
                .select(qna.count())
                .from(qna)
                .fetchOne();

        return new PageImpl<>(qnaList, pageable, count);
    }

    public BooleanExpression likeQnaId(String qnaId){
        return qnaId != null ? qna.subject.contains(qnaId) : null ;
    }

    /**
     * Review 전체 리스트
     */
    @Override
    public Page<Product_reviews> findAll(Pageable pageable, String sortReviewCond, SearchReviewCondition searchReviewCond) {

        List<Product_reviews> reviewList = query
                .select(product_reviews)
                .from(product_reviews)
                .where(likeReviewId(searchReviewCond.getReviewId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query
                .select(product_reviews.count())
                .from(product_reviews)
                .fetchOne();

        return new PageImpl<>(reviewList, pageable, count);
    }

    public BooleanExpression likeReviewId(String reviewId){
        return reviewId != null ? product_reviews.content.contains(reviewId) : null ;
    }


}
