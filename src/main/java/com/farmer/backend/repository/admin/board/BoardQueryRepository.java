package com.farmer.backend.repository.admin.board;

import com.farmer.backend.dto.admin.board.SearchQnaCondition;
import com.farmer.backend.dto.admin.board.SearchReviewCondition;
import com.farmer.backend.entity.Product_reviews;
import com.farmer.backend.entity.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardQueryRepository

{

    Page<Qna> findAll(Pageable pageable, String sortQnaCond, SearchQnaCondition searchQnaCondition);

    Page<Product_reviews> findAll(Pageable pageable, String sortReviewCond, SearchReviewCondition searchReviewCondition);


    void deleteQna(Long qnaId);

    void deleteReview(Long reviewId);

    Page<Product_reviews> searchReviewList(Pageable pageable, SearchReviewCondition searchReviewCondition);

    Page<Qna> searchQnAList(Pageable pageable, SearchQnaCondition searchQnaCondition);
}
