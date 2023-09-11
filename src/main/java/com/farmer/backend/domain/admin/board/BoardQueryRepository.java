package com.farmer.backend.domain.admin.board;

import com.farmer.backend.domain.admin.notice.Notice;
import com.farmer.backend.domain.admin.qna.Qna;
import com.farmer.backend.domain.admin.faq.Faq;
import com.farmer.backend.domain.orderproduct.OrderProduct;
import com.farmer.backend.domain.product.productreview.ProductReviews;
import com.farmer.backend.api.controller.user.faq.request.SearchFaqCondition;
import com.farmer.backend.api.controller.user.notice.request.SearchNoticeCondition;
import com.farmer.backend.api.controller.user.qna.request.SearchQnaCondition;
import com.farmer.backend.api.controller.user.review.request.SearchReviewCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardQueryRepository

{

    Page<Qna> findAll(Pageable pageable, String sortQnaCond, SearchQnaCondition searchQnaCondition);

    Page<ProductReviews> findAll(Pageable pageable, String sortReviewCond, SearchReviewCondition searchReviewCondition);

    Page<Notice> findAll(Pageable pageable, String sortNoticeCond, SearchNoticeCondition searchNoticeCondition);

    Page<Faq> findAll(Pageable pageable, String sortQnaCond, SearchFaqCondition searchFaqCondition);

    List<OrderProduct> orderProductFindAll();

    void deleteQna(Long qnaId);

    void deleteReview(Long reviewId);

    void deleteNotice(Long noticeId);

    void deleteFaq(Long faqId);

    Page<ProductReviews> searchReviewList(Pageable pageable, SearchReviewCondition searchReviewCondition);

    Page<Qna> searchQnAList(Pageable pageable, SearchQnaCondition searchQnaCondition);

    Page<Notice> searchNoticeList(Pageable pageable, SearchNoticeCondition searchNoticeCondition);


    Page<Faq> searchFaqList(Pageable pageable, SearchFaqCondition searchFaqCond);

}
