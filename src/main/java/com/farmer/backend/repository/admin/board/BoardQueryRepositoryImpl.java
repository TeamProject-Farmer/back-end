package com.farmer.backend.repository.admin.board;

import com.farmer.backend.dto.admin.board.faq.SearchFaqCondition;
import com.farmer.backend.dto.admin.board.notice.SearchNoticeCondition;
import com.farmer.backend.dto.admin.board.qna.SearchQnaCondition;
import com.farmer.backend.dto.admin.board.review.SearchReviewCondition;
import com.farmer.backend.entity.*;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

import static com.farmer.backend.entity.QFaq.faq;
import static com.farmer.backend.entity.QNotice.notice;
import static com.farmer.backend.entity.QOrderDetail.orderDetail;
import static com.farmer.backend.entity.QOrders.orders;
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
                .where(likeQnAUserEmail(searchCond.getUserEmail()),likeQnAUsername(searchCond.getUserName()),likeQnAProductName(searchCond.getProductName()))
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
                .where(likeReviewUsername(searchReviewCond.getUserName()),likeReviewUserEmail(searchReviewCond.getUserEmail()))
                .orderBy(sortOrderReview(sortReviewCond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query
                .select(product_reviews.count())
                .from(product_reviews)
                .fetchOne();

        return new PageImpl<>(reviewList,pageable, count);
    }

    /**
     * orderDetail 전체 리스트
     */
    @Override
    public List<OrderDetail> orderProductFindAll(){
        List<OrderDetail> orderProduct = query
                .select(orderDetail)
                .from(orderDetail)
                .fetch();

        return orderProduct;
    }

    /**
     * 공지사항 전체 리스트
     */
    public Page<Notice> findAll(Pageable pageable, String sortNoticeCond, SearchNoticeCondition searchNoticeCond){

        List<Notice> noticeList = query
                .select(notice)
                .from(notice)
                .where(likeNoticeTitle(searchNoticeCond.getTitle()),likeNoticeUserEmail(searchNoticeCond.getUserEmail()),
                        likeNoticeUserName(searchNoticeCond.getUserName()))
                .orderBy(sortOrderNotice(sortNoticeCond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query
                .select(notice.count())
                .from(notice)
                .fetchOne();

        return new PageImpl<>(noticeList,pageable,count);
    }

    /**
     * 자주묻는 질문 전체 리스트
     */
    public Page<Faq> findAll(Pageable pageable , String sortFaqCond, SearchFaqCondition searchFaqCond){

        List<Faq> faqList = query
                .select(faq)
                .from(faq)
                .where(likeFaqCategoryName(searchFaqCond.getCategoryName()),likeFaqUserName(searchFaqCond.getUserName()),
                        likeFaqUserEmail(searchFaqCond.getUserEmail()))
                .orderBy(sortOrderFaq(sortFaqCond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query
                .select(faq.count())
                .from(faq)
                .fetchOne();

        return new PageImpl<>(faqList,pageable,count);

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
     * 공지사항 삭제
     */
    @Override
    public void deleteNotice(Long noticeId){
        query
                .delete(notice)
                .where(notice.id.eq(noticeId))
                .execute();
    }

    /**
     * 자주 묻는 질문 삭제
     * @param faqId
     */
    @Override
    public void deleteFaq(Long faqId){
        query
                .delete(faq)
                .where(faq.id.eq(faqId))
                .execute();
    }

    /**
     * QnA검색
     */
    @Override
    public Page<Qna> searchQnAList(Pageable pageable,SearchQnaCondition cond) {
        List<Qna> qnaList = query.selectFrom(qna)
                .where(likeQnAUserEmail(cond.getUserEmail()),likeQnAUsername(cond.getUserName()),likeQnAProductName(cond.getProductName()))
                .orderBy(qna.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query
                .select(qna.count())
                .from(qna)
                .where(likeQnAUserEmail(cond.getUserEmail()),likeQnAUsername(cond.getUserName()),likeQnAProductName(cond.getProductName()))
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
                .where(likeReviewUserEmail(cond.getUserEmail()),likeReviewUsername(cond.getUserName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(product_reviews.id.asc())
                .fetch();

        Long count=query
                .select(product_reviews.count())
                .from(product_reviews)
                .where(likeReviewUsername(cond.getUserName()),likeReviewUserEmail(cond.getUserEmail()))
                .fetchOne();

        return new PageImpl<>(reviewsList,pageable,count);
    }

    public BooleanExpression likeReviewUsername(String userName){
        return userName != null ? product_reviews.member.username.contains(userName) : null ;
    }

    public BooleanExpression likeReviewUserEmail(String userEmail){
        return userEmail != null ? product_reviews.member.email.contains(userEmail) : null ;
    }

    /**
     * 공지사항 검색
     */
    @Override
    public Page<Notice> searchNoticeList(Pageable pageable,SearchNoticeCondition cond){
        List<Notice> noticeList = query.selectFrom(notice)
                .where(likeNoticeUserEmail(cond.getUserEmail()),likeNoticeUserName(cond.getUserName()),likeNoticeTitle(cond.getTitle()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(notice.id.asc())
                .fetch();

        Long count=query
                .select(notice.count())
                .from(notice)
                .where(likeNoticeUserName(cond.getUserName()),likeNoticeUserEmail(cond.getUserEmail()))
                .fetchOne();

        return new PageImpl<>(noticeList,pageable,count);
    }

    private BooleanExpression likeNoticeUserEmail(String userEmail) {
        return userEmail != null?notice.member.email.contains(userEmail):null;
    }
    private BooleanExpression likeNoticeUserName(String userName) {
        return userName != null?notice.member.username.contains(userName):null;
    }
    private BooleanExpression likeNoticeTitle(String title){
        return title != null ?notice.title.contains(title):null;
    }

    /**
     * 자주 묻는 질문 검색
     */
    @Override
    public Page<Faq> searchFaqList(Pageable pageable, SearchFaqCondition cond){
        List<Faq> faqList = query.selectFrom(faq)
                .where(likeFaqUserEmail(cond.getUserEmail()),likeFaqUserName(cond.getUserName()),likeFaqCategoryName(cond.getCategoryName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(faq.id.asc())
                .fetch();

        Long count=query
                .select(faq.count())
                .from(faq)
                .where(likeFaqUserName(cond.getUserName()),likeFaqUserEmail(cond.getUserEmail()),likeFaqCategoryName(cond.getCategoryName()))
                .fetchOne();

        return new PageImpl<>(faqList,pageable,count);
    }
    private BooleanExpression likeFaqUserName(String userName) {
        return userName != null?faq.member.username.contains(userName):null;
    }

    private BooleanExpression likeFaqUserEmail(String userEmail) {
        return userEmail != null?faq.member.email.contains(userEmail):null;
    }
    private BooleanExpression likeFaqCategoryName(String categoryName){
        return categoryName != null ? faq.faqCategory.name.contains(categoryName):null;
    }

    /**
     * QnA 정렬
     */
    public OrderSpecifier<?> sortOrderQna(String sortOrderCond){

        Order order=Order.DESC;
        Order order_A= Order.ASC;

        if(Objects.isNull(sortOrderCond) || sortOrderCond.equals("qCreatedDate")){
            return new OrderSpecifier<>(order,qna.id);
        } else if (sortOrderCond.equals("aCreatedDate")){
            return new OrderSpecifier<>(order,qna.aCreatedDate);
        }else if(sortOrderCond.equals("userName")){
            return new OrderSpecifier<>(order_A,qna.member.username);
        }else if(sortOrderCond.equals("productName")){
            return new OrderSpecifier<>(order_A,qna.product.name);
        }
        return new OrderSpecifier(Order.DESC,NullExpression.DEFAULT,OrderSpecifier.NullHandling.Default);
    }

    /**
     * Review 정렬
     */
    public OrderSpecifier<?> sortOrderReview(String sortOrderCond){

        Order order=Order.DESC;
        Order order_A= Order.ASC;

        if(Objects.isNull(sortOrderCond) || sortOrderCond.equals("createdDate")){
            return new OrderSpecifier<>(order,product_reviews.id);

        }else if (sortOrderCond.equals("userName")){
            return new OrderSpecifier<>(order_A,product_reviews.member.username);
        }

        return new OrderSpecifier(Order.DESC,NullExpression.DEFAULT,OrderSpecifier.NullHandling.Default);
    }

    /**
     * 공지사항 정렬
     */
    public OrderSpecifier<?> sortOrderNotice(String sortOrderCond){
        Order order=Order.DESC;
        Order order_A= Order.ASC;

        if(Objects.isNull(sortOrderCond) || sortOrderCond.equals("noticeId") || sortOrderCond.equals("createdDate")){
            return new OrderSpecifier<>(order,notice.id);
        }
        else if (sortOrderCond.equals("userName")){
            return new OrderSpecifier<>(order_A,notice.member.username);
        }
        else if(sortOrderCond.equals("userEmail")){
            return new OrderSpecifier<>(order_A,notice.member.email);
        }

        return new OrderSpecifier(Order.DESC,NullExpression.DEFAULT,OrderSpecifier.NullHandling.Default);

    }

    /**
     * 자주 묻는 질문 정렬
     */
    public OrderSpecifier<?> sortOrderFaq(String sortOrderCond){
        Order order=Order.DESC;
        Order order_A= Order.ASC;

        if(Objects.isNull(sortOrderCond) || sortOrderCond.equals("faqId") || sortOrderCond.equals("createdDate")){
            return new OrderSpecifier<>(order,faq.id);
        }
        else if (sortOrderCond.equals("userName")){
            return new OrderSpecifier<>(order_A,faq.member.username);
        }
        else if(sortOrderCond.equals("userEmail")){
            return new OrderSpecifier<>(order_A,faq.member.email);
        }
        else if (sortOrderCond.equals("categoryName")){
            return new OrderSpecifier<>(order_A,faq.faqCategory.name);
        }

        return new OrderSpecifier(Order.DESC,NullExpression.DEFAULT,OrderSpecifier.NullHandling.Default);

    }

}
