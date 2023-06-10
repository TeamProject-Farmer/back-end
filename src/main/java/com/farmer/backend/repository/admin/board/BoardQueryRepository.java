package com.farmer.backend.repository.admin.board;

import com.farmer.backend.dto.admin.board.faq.SearchFaqCondition;
import com.farmer.backend.dto.admin.board.notice.SearchNoticeCondition;
import com.farmer.backend.dto.admin.board.qna.SearchQnaCondition;
import com.farmer.backend.dto.admin.board.review.SearchReviewCondition;
import com.farmer.backend.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardQueryRepository

{

    Page<Qna> findAll(Pageable pageable, String sortQnaCond, SearchQnaCondition searchQnaCondition);

    Page<Product_reviews> findAll(Pageable pageable, String sortReviewCond, SearchReviewCondition searchReviewCondition);

    Page<Notice> findAll(Pageable pageable, String sortNoticeCond, SearchNoticeCondition searchNoticeCondition);

    Page<Faq> findAll(Pageable pageable, String sortQnaCond, SearchFaqCondition searchFaqCondition);

    List<OrderDetail> orderProductFindAll();

    void deleteQna(Long qnaId);

    void deleteReview(Long reviewId);

    void deleteNotice(Long noticeId);

    void deleteFaq(Long faqId);

    Page<Product_reviews> searchReviewList(Pageable pageable, SearchReviewCondition searchReviewCondition);

    Page<Qna> searchQnAList(Pageable pageable, SearchQnaCondition searchQnaCondition);

    Page<Notice> searchNoticeList(Pageable pageable, SearchNoticeCondition searchNoticeCondition);


    Page<Faq> searchFaqList(Pageable pageable, SearchFaqCondition searchFaqCond);

}
