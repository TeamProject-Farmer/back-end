package com.farmer.backend.repository.admin.board;

import com.farmer.backend.dto.admin.board.SearchQnaCondition;
import com.farmer.backend.dto.admin.board.SearchReviewCondition;
import com.farmer.backend.entity.OrderDetail;
import com.farmer.backend.entity.Product_reviews;
import com.farmer.backend.entity.Qna;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

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
    public Page<Qna> findAll(Pageable pageable, String sortQnaCond, SearchQnaCondition searchCond) {

        List<Qna> qnaList = query
                .select(qna)
                .from(qna)
                .where(likeQnAUserEmail(searchCond.getUserEmail()),likeQnAUsername(searchCond.getUserEmail()),likeQnAProductName(searchCond.getProductName()))
                .orderBy(sortOrderQna(sortQnaCond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query
                .select(qna.count())
                .from(qna)
                .fetchOne();

        return new PageImpl<>(qnaList, pageable, count);
    }


    /**
     * Review 전체 리스트
     */
    @Override
    public Page<Product_reviews> findAll(Pageable pageable, String sortReviewCond, SearchReviewCondition searchReviewCond) {

        List<Product_reviews> reviewList = query
                .select(product_reviews)
                .from(product_reviews)
                .where(likeReviewUsername(searchReviewCond.getUsername()),likeReviewUserEmail(searchReviewCond.getEmail()))
                .orderBy(sortOrderReview(sortReviewCond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query
                .select(product_reviews.count())
                .from(product_reviews)
                .fetchOne();

        return new PageImpl<>(reviewList, pageable, count);
    }


    /**
     * QnA 삭제
     * @param qnaId
     */
    @Override
    public void deleteQna(Long qnaId){
        query
                .delete(qna)
                .where(qna.id.eq(qnaId))
                .execute();
    }

    /**
     * Review 삭제
     * @param reviewId
     */
    @Override
    public void deleteReview(Long reviewId){
        query
                .delete(product_reviews)
                .where(product_reviews.id.eq(reviewId))
                .execute();
    }



    /**
     * QnA검색
     */
    @Override
    public Page<Qna> searchQnAList(Pageable pageable,SearchQnaCondition cond) {
        List<Qna> qnaList = query.selectFrom(qna)
                .where(likeQnAUserEmail(cond.getUserEmail()),likeQnAUsername(cond.getUserEmail()),likeQnAProductName(cond.getProductName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qna.id.desc())
                .fetch();

        Long count = query
                .select(qna.count())
                .from(qna)
                .where(likeQnAUserEmail(cond.getUserEmail()),likeQnAUsername(cond.getUsername()),likeQnAProductName(cond.getProductName()))
                .fetchOne();

        return new PageImpl<>(qnaList, pageable, count);
    }

    public BooleanExpression likeQnAUsername(String userName){
        return userName != null ? qna.member.username.contains(userName) : null ;
    }

    public BooleanExpression likeQnAUserEmail(String userEmail){
        return userEmail != null ? qna.member.email.contains(userEmail) : null ;
    }

    public BooleanExpression likeQnAProductName(String productName){
        return productName != null ? qna.product.name.contains(productName):null;
    }

    /**
     * Review 검색
     */
    @Override
    public Page<Product_reviews> searchReviewList(Pageable pageable,SearchReviewCondition cond){
        List<Product_reviews> reviewsList = query.selectFrom(product_reviews)
                .where(likeReviewUserEmail(cond.getEmail()),likeReviewUsername(cond.getUsername()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(product_reviews.id.desc())
                .fetch();

        Long count=query
                .select(product_reviews.count())
                .from(product_reviews)
                .where(likeReviewUsername(cond.getUsername()),likeReviewUserEmail(cond.getEmail()))
                .fetchOne();

        return new PageImpl<>(reviewsList,pageable,count);
    }

    public BooleanExpression likeReviewUsername(String userName){
        return userName != null ? product_reviews.member.username.contains(userName) : null ;
    }

    public BooleanExpression likeReviewUserEmail(String userEmail){
        return userEmail != null ? product_reviews.member.email.contains(userEmail) : null ;
    }


    public OrderSpecifier<?> sortOrderQna(String sortOrderCond){

        Order order=Order.DESC;
        Order order_A= Order.ASC;

        if(Objects.isNull(sortOrderCond) || sortOrderCond.equals("qCreatedDate")){
            return new OrderSpecifier<>(order,qna.id);
        } else if (sortOrderCond.equals("aCreatedDate")){
            return new OrderSpecifier<>(order_A,qna.aCreatedDate);
        }else if(sortOrderCond.equals("userName")){
            return new OrderSpecifier<>(order,qna.member.username);
        }else if(sortOrderCond.equals("productName")){
            return new OrderSpecifier<>(order,qna.product.name);
        }
        return new OrderSpecifier(Order.DESC,NullExpression.DEFAULT,OrderSpecifier.NullHandling.Default);
    }

    public OrderSpecifier<?> sortOrderReview(String sortOrderCond){

        Order order=Order.DESC;

        if(Objects.isNull(sortOrderCond) || sortOrderCond.equals("createdDate")){
            return new OrderSpecifier<>(order,product_reviews.id);
        }else if (sortOrderCond.equals("memberName")){
            return new OrderSpecifier<>(order,product_reviews.member.username);
        }

        return new OrderSpecifier(Order.DESC,NullExpression.DEFAULT,OrderSpecifier.NullHandling.Default);
    }
}
