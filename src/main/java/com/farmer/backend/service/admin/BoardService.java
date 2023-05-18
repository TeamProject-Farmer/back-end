package com.farmer.backend.service.admin;

import com.farmer.backend.dto.admin.board.*;
import com.farmer.backend.entity.Orders;
import com.farmer.backend.entity.Product;
import com.farmer.backend.entity.Product_reviews;
import com.farmer.backend.entity.Qna;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.repository.admin.board.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardQueryRepository boardQueryRepositoryImpl;
    private final QnARepository qnaRepository;
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;


    /**
     * QnA 전체 리스트 조회
     */
    @Transactional(readOnly = true)
    public Page<ResponseBoardQnADto> qnaList(Pageable pageable, String sortQnaCond, SearchQnaCondition searchQnaCondition) {
        return boardQueryRepositoryImpl.findAll(pageable,sortQnaCond,searchQnaCondition).map(Qna::qnaList);
    }


    /**
     *QnA 단건 조회
     */
    @Transactional(readOnly = true)
    public ResponseBoardQnADto findOneQnA(Long qnaId) {
        Optional<Qna> findQnA= qnaRepository.findById(qnaId);
        return findQnA.map(qna -> ResponseBoardQnADto.getQnA(qna)).orElseThrow(()-> new CustomException(ErrorCode.QNA_NOT_FOUND));
    }

    /**
     * qna 수정
     */
    @Transactional
    public Long updateQnA(RequestBoardQnADto qnaDto) {
        Product product = productRepository.findById(qnaDto.getProduct_id()).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        qnaRepository.findById(qnaDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.QNA_NOT_FOUND)).updateQnA(qnaDto, product);
        return qnaDto.getId();
    }


    /**
     * Review 전체 리스트 조회
     */
    @Transactional(readOnly = true)
    public Page<ResponseBoardReviewDto> reviewList(Pageable pageable, String sortReviewCond, SearchReviewCondition searchReviewCondition) {
        return boardQueryRepositoryImpl.findAll(pageable,sortReviewCond,searchReviewCondition).map(Product_reviews::reviewList);
    }

    /**
     *Review 단건 조회
     */
    @Transactional(readOnly = true)
    public ResponseBoardReviewDto findOneReview(Long reviewId) {
        Optional<Product_reviews> findReview= reviewRepository.findById(reviewId);
        return findReview.map(product_reviews -> ResponseBoardReviewDto.getReview(product_reviews)).orElseThrow(()-> new CustomException(ErrorCode.QNA_NOT_FOUND));
    }

    @Transactional
    public Long updateReview(RequestBoardReviewDto reviewDto) {
        Orders orders = ordersRepository.findById(reviewDto.getOrders_id()).orElseThrow(()-> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        reviewRepository.findById(reviewDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND)).updateReview(reviewDto,orders);

        return reviewDto.getId();
    }
}
