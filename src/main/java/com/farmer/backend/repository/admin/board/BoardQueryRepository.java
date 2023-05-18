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



}
