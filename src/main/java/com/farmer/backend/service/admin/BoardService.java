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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardQueryRepository boardQueryRepositoryImpl;
    private final QnARepository qnaRepository;
    private final ReviewRepository reviewRepository;


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
     * QnA 검색
     * @param pageable
     * @param searchQnaCondition
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ResponseBoardQnADto> searchQnAList(Pageable pageable, SearchQnaCondition searchQnaCondition) {
        Page<Qna> QnAList=boardQueryRepositoryImpl.searchQnAList(pageable,searchQnaCondition);
        return new PageImpl<>(QnAList.stream().map(qna -> ResponseBoardQnADto.getQnA(qna)).collect(Collectors.toList()));

    }

    /**
     *  QNA 추가 (답변 달기)
     */
    @Transactional
    public String addAns(RequestBoardQnADto answerDto, Long qnaId) {
        qnaRepository.findById(qnaId).orElseThrow(()-> new CustomException(ErrorCode.QNA_NOT_FOUND)).addAnswer(answerDto);
        log.info(answerDto.getAnswer());
        return answerDto.getAnswer();
    }

    /**
     *  QNA 수정
     */
    @Transactional
    public Long updateQnA(RequestBoardQnADto qnaDto,Long qnaId) {
        qnaRepository.findById(qnaId).orElseThrow(() -> new CustomException(ErrorCode.QNA_NOT_FOUND)).updateQnA(qnaDto);

        return qnaDto.getId();
    }

    /**
     * QNA 삭제
     */

    @Transactional
    public void delQna(Long qnaId){
        Qna qna= qnaRepository.findById(qnaId).orElseThrow(()-> new CustomException(ErrorCode.QNA_NOT_FOUND));
        boardQueryRepositoryImpl.deleteQna(qna.getId());
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

    /**
     * 리뷰 검색 리스트
     * @param pageable 페이징
     * @param searchReviewCondition 검색정보
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ResponseBoardReviewDto> searchReviewList(Pageable pageable, SearchReviewCondition searchReviewCondition) {
        Page<Product_reviews> reviewList=boardQueryRepositoryImpl.searchReviewList(pageable,searchReviewCondition);
        return new PageImpl<>(reviewList.stream().map(product_reviews -> ResponseBoardReviewDto.getReview(product_reviews)).collect(Collectors.toList()));
    }

    /**
     * Review 수정
     */
    @Transactional
    public Long updateReview(RequestBoardReviewDto reviewDto, Long reviewId) {
        reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND)).updateReview(reviewDto);

        return reviewDto.getId();
    }

    /**
     * Review 삭제
     */
    @Transactional
    public void delReview(Long reviewId) {
        Product_reviews review = reviewRepository.findById(reviewId).orElseThrow(()-> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        boardQueryRepositoryImpl.deleteReview(review.getId());

    }

    /**
     * Review 추가
     * @param reviewDto REVIEW 데이터
     */
    public void addReview(RequestBoardReviewDto reviewDto) {

        log.info(String.valueOf(reviewRepository.findById(reviewDto.getId())));
        if (reviewRepository.findById(reviewDto.getId()).isPresent()){
            throw new CustomException(ErrorCode.REVIEW_FOUND);
        };
        reviewRepository.save(reviewDto.toEntity());
    }




}
