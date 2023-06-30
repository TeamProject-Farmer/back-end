package com.farmer.backend.api.service.admin;

import com.farmer.backend.domain.admin.board.BoardQueryRepository;
import com.farmer.backend.domain.admin.board.NoticeRepository;
import com.farmer.backend.domain.admin.board.QnARepository;
import com.farmer.backend.domain.admin.board.ReviewRepository;
import com.farmer.backend.domain.admin.notice.Notice;
import com.farmer.backend.domain.admin.qna.Qna;
import com.farmer.backend.domain.admin.faq.Faq;
import com.farmer.backend.domain.admin.faq.faqcategory.FaqCategoryRepository;
import com.farmer.backend.domain.admin.faq.FaqRepository;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.orderproduct.OrderProduct;
import com.farmer.backend.domain.product.productreview.ProductReviews;
import com.farmer.backend.api.controller.admin.faq.request.RequestFaqDto;
import com.farmer.backend.api.controller.admin.faq.response.ResponseFaqDto;
import com.farmer.backend.api.controller.admin.faq.request.SearchFaqCondition;
import com.farmer.backend.api.controller.admin.notice.request.RequestNoticeDto;
import com.farmer.backend.api.controller.admin.notice.response.ResponseNoticeDto;
import com.farmer.backend.api.controller.admin.notice.request.SearchNoticeCondition;
import com.farmer.backend.api.controller.admin.qna.request.RequestBoardQnADto;
import com.farmer.backend.api.controller.admin.qna.response.ResponseBoardQnADto;
import com.farmer.backend.api.controller.admin.qna.request.SearchQnaCondition;
import com.farmer.backend.api.controller.admin.review.response.ResponseBoardReviewDto;
import com.farmer.backend.api.controller.admin.review.request.SearchReviewCondition;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardQueryRepository boardQueryRepositoryImpl;
    private final QnARepository qnaRepository;
    private final ReviewRepository reviewRepository;
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    private final FaqRepository faqRepository;
    private final FaqCategoryRepository faqCategoryRepository;

    /**
     * QnA 전체 리스트 조회
     * @param pageable 페이징
     * @param sortQnaCond 정렬값 (question 날짜순, answer 날짜순, 회원 이름순, 상품 이름순)
     * @param searchQnaCondition 검색값 (회원 이름, 회원 이메일, 상품이름)
     * @return Page<ResponseBoardQnADto>
     */
    @Transactional(readOnly = true)
    public Page<ResponseBoardQnADto> qnaList(Pageable pageable, String sortQnaCond, SearchQnaCondition searchQnaCondition) {

        return boardQueryRepositoryImpl.findAll(pageable,sortQnaCond,searchQnaCondition).map(Qna::qnaList);
    }


    /**
     * QnA 단건 조회
     * @param qnaId 조회할 QnA ID
     * @return ResponseBoardQnADto
     */

    @Transactional(readOnly = true)
    public ResponseBoardQnADto findOneQnA(Long qnaId) {
        Optional<Qna> findQnA= qnaRepository.findById(qnaId);
        return findQnA.map(qna -> ResponseBoardQnADto.getQnA(qna)).orElseThrow(()-> new CustomException(ErrorCode.QNA_NOT_FOUND));
    }

    /**
     * QnA 검색
     * @param pageable 페이징
     * @param searchQnaCondition 검색값 (회원 이름, 회원 이메일, 상품이름)
     * @return Page<ResponseBoardQnADto>
     */
    @Transactional(readOnly = true)
    public Page<ResponseBoardQnADto> searchQnAList(Pageable pageable, SearchQnaCondition searchQnaCondition) {
        Page<Qna> QnAList=boardQueryRepositoryImpl.searchQnAList(pageable,searchQnaCondition);
        return new PageImpl<>(QnAList.stream().map(qna -> ResponseBoardQnADto.getQnA(qna)).collect(Collectors.toList()));

    }

    /**
     *  QNA 추가 (답변 달기)
     * @param answerDto 답변 데이터
     * @param qnaId 답변을 달아줄 QnA ID
     */
    @Transactional
    public void addAns(RequestBoardQnADto answerDto, Long qnaId) {

        qnaRepository.findById(qnaId).orElseThrow(()-> new CustomException(ErrorCode.QNA_NOT_FOUND)).qnaAnswer(answerDto);

    }

    /**
     * QnA 답변 수정
     * @param qnaDto QnA 답변 데이터
     * @param qnaId 수정할 QnA ID
     */
    @Transactional
    public void updateQnA(RequestBoardQnADto qnaDto,Long qnaId) {
        qnaRepository.findById(qnaId).orElseThrow(() -> new CustomException(ErrorCode.QNA_NOT_FOUND)).qnaAnswer(qnaDto);

    }

    /**
     * QnA 삭제
     * @param qnaId 삭제할 QnA ID
     */
    @Transactional
    public void delQna(Long qnaId){
        Qna qna= qnaRepository.findById(qnaId).orElseThrow(()-> new CustomException(ErrorCode.QNA_NOT_FOUND));
        boardQueryRepositoryImpl.deleteQna(qna.getId());
    }

    /**
     * Review 전체 리스트 조회
     * @param pageable 페이징
     * @param sortReviewCond 정렬값 (리뷰 날짜순, 회원 이름순)
     * @param searchReviewCondition 검색값 (회원 이름, 회원 이메일)
     * @return Page<ResponseBoardReviewDto>
     */
    @Transactional(readOnly = true)
    public Page<ResponseBoardReviewDto> reviewList(Pageable pageable, String sortReviewCond, SearchReviewCondition searchReviewCondition) {
        return null;
    }

    /**
     * Review 단건 조회
     * @param reviewId 조회할 Review ID
     * @return ResponseBoardReviewDto
     */
    @Transactional(readOnly = true)
    public ResponseBoardReviewDto findOneReview(Long reviewId) {
        return null;
    }

    /**
     * 리뷰 검색 리스트
     * @param pageable 페이징
     * @param searchReviewCondition 검색값 (회원 이름, 회원 이메일)
     * @return Page<ResponseBoardReviewDto>
     */
    @Transactional(readOnly = true)
    public Page<ResponseBoardReviewDto> searchReviewList(Pageable pageable, SearchReviewCondition searchReviewCondition) {
        return null;
    }

    /**
     * Review 삭제
     * @param reviewId 삭제할 review ID
     */
    @Transactional
    public void delReview(Long reviewId) {
        ProductReviews review = reviewRepository.findById(reviewId).orElseThrow(()-> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        boardQueryRepositoryImpl.deleteReview(review.getId());

    }


    /**
     * 공지사항 전체 리스트 조회
     * @param pageable 페이징
     * @param searchNoticeCondition 검색값 (제목, 회원 이메일, 회원 이름)
     * @param sortNoticeCond 정렬값 (공지 날짜, 회원 이름, 회원 이메일)
     * @return Page<ResponseNoticeDto>
     */

    @Transactional(readOnly = true)
    public Page<ResponseNoticeDto> noticeList(Pageable pageable, SearchNoticeCondition searchNoticeCondition, String sortNoticeCond) {

        return boardQueryRepositoryImpl.findAll(pageable,sortNoticeCond,searchNoticeCondition).map(Notice::noticeList);
    }

    /**
     * 공지사항 단건 조회
     * @param noticeId 조회할 공지사항 ID
     * @return ResponseNoticeDto
     */
    @Transactional(readOnly = true)
    public ResponseNoticeDto findOneNotice(Long noticeId) {
        Optional<Notice> findNotice= noticeRepository.findById(noticeId);
        return findNotice.map(notice -> ResponseNoticeDto.getNoticeList(notice)).orElseThrow(()-> new CustomException(ErrorCode.NOTICE_NOT_FOUND));
    }

    /**
     * 공지사항 검색
     * @param pageable 페이징
     * @param searchNoticeCondition 검색값 (관리자 이름, 관리자 이메일, 공지사항 제목)
     * @return Page<ResponseNoticeDto>
     */
    public Page<ResponseNoticeDto> searchNoticeList(Pageable pageable, SearchNoticeCondition searchNoticeCondition) {

        Page<Notice> noticeList=boardQueryRepositoryImpl.searchNoticeList(pageable,searchNoticeCondition);
        return new PageImpl<>(noticeList.stream().map(notice -> ResponseNoticeDto.getNoticeList(notice)).collect(Collectors.toList()));

    }

    /**
     * 공지사항 추가
     * @param noticeDto 공지사항 데이터
     */
    @Transactional
    public void addNotice(RequestNoticeDto noticeDto) {

        Member member = memberRepository.findById(noticeDto.getMemberId()).orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        noticeRepository.save(noticeDto.toEntity(member));
    }

    /**
     * 공지사항 수정
     * @param noticeDto 공지사항 데이터
     * @param noticeId 수정할 공지사항 ID
     */
    @Transactional
    public void updateNotice(RequestNoticeDto noticeDto, Long noticeId) {
        Member member=memberRepository.findById(noticeDto.getMemberId()).orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        noticeRepository.findById(noticeId).orElseThrow(()-> new CustomException(ErrorCode.NOTICE_NOT_FOUND)).updateNotice(noticeDto,member);

    }

    /**
     * 공지사항 삭제
     * @param noticeId 삭제할 공지사항 ID
     */
    @Transactional
    public void delNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(()-> new CustomException(ErrorCode.NOTICE_NOT_FOUND));
        boardQueryRepositoryImpl.deleteNotice(notice.getId());
    }

    /**
     * 자주묻는 질문 전체 리스트 조회
     * @param pageable 페이징
     * @param searchFaqCondition 검색값 (카테고리 이름, 회원 이름, 회원 이메일)
     * @param sortFaqCond 정렬값 (생성 날짜, 회원 이름, 회원 이메일, 카테고리 이름)
     * @return Page<ResponseFaqDto>
     */
    @Transactional(readOnly = true)
    public Page<ResponseFaqDto> faqList(Pageable pageable, SearchFaqCondition searchFaqCondition, String sortFaqCond) {

        return boardQueryRepositoryImpl.findAll(pageable,sortFaqCond,searchFaqCondition).map(Faq::faqList);

    }

    /**
     * 자주묻는 질문 단건 조회
     * @param faqId 조회할 자주 묻는 질문 ID
     * @return ResponseFaqDto
     */
    @Transactional(readOnly = true)
    public ResponseFaqDto findOneFaq(Long faqId) {
        Optional<Faq> findFaq= faqRepository.findById(faqId);
        return findFaq.map(faq -> ResponseFaqDto.getFaqList(faq)).orElseThrow(()-> new CustomException(ErrorCode.FAQ_NOT_FOUND));
    }

    /**
     * 자주 묻는 질문 검색
     * @param pageable 페이징
     * @param searchFaqCond 검색값 (회원 이름, 회원 이메일, 카테고리 이름)
     * @return Page<ResponseFaqDto>
     */
    @Transactional
    public Page<ResponseFaqDto> searchFaqList(Pageable pageable, SearchFaqCondition searchFaqCond) {

        Page<Faq> faqList=boardQueryRepositoryImpl.searchFaqList(pageable,searchFaqCond);
        return new PageImpl<>(faqList.stream().map(faq -> ResponseFaqDto.getFaqList(faq)).collect(Collectors.toList()));
    }

    /**
     * 자주 묻는 질문 답변 추가
     * @param faqDto 자주 묻는 질문 답변
     * @param faqId 답변 추가할 자주 묻는 질문 ID
     */
    @Transactional
    public void faqAddAnswer(RequestFaqDto faqDto, Long faqId) {
        faqRepository.findById(faqId).orElseThrow(()-> new CustomException(ErrorCode.FAQ_NOT_FOUND)).faqAnswer(faqDto);

    }

    /**
     * 자주 묻는 질문 답변 수정
     * @param faqDto 자주 묻는 질문 답변
     * @param faqId 수정할 자주 묻는 질문 ID
     */
    @Transactional
    public void updateFaq(RequestFaqDto faqDto, Long faqId) {

        faqRepository.findById(faqId).orElseThrow(()-> new CustomException(ErrorCode.FAQ_NOT_FOUND)).faqAnswer(faqDto);

    }

    /**
     * 자주 묻는 질문 삭제
     * @param faqId 삭제할 자주 묻는 질문 ID
     */
    @Transactional
    public void delFaq(Long faqId) {
        Faq faq = faqRepository.findById(faqId).orElseThrow(()->new CustomException(ErrorCode.FAQ_NOT_FOUND));
        boardQueryRepositoryImpl.deleteFaq(faq.getId());
    }

}
