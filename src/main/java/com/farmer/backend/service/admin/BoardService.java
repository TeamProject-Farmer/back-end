package com.farmer.backend.service.admin;

import com.farmer.backend.dto.admin.board.faq.RequestFaqDto;
import com.farmer.backend.dto.admin.board.faq.ResponseFaqDto;
import com.farmer.backend.dto.admin.board.faq.SearchFaqCondition;
import com.farmer.backend.dto.admin.board.notice.RequestNoticeDto;
import com.farmer.backend.dto.admin.board.notice.ResponseNoticeDto;
import com.farmer.backend.dto.admin.board.notice.SearchNoticeCondition;
import com.farmer.backend.dto.admin.board.qna.RequestBoardQnADto;
import com.farmer.backend.dto.admin.board.qna.ResponseBoardQnADto;
import com.farmer.backend.dto.admin.board.qna.SearchQnaCondition;
import com.farmer.backend.dto.admin.board.review.RequestBoardReviewDto;
import com.farmer.backend.dto.admin.board.review.ResponseBoardReviewDto;
import com.farmer.backend.dto.admin.board.review.SearchReviewCondition;
import com.farmer.backend.entity.*;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.repository.admin.board.*;
import com.farmer.backend.repository.admin.member.MemberRepository;
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
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    private final FaqRepository faqRepository;
    private final FaqCategoryRepository faqCategoryRepository;
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
    @Transactional
    public void addReview(RequestBoardReviewDto reviewDto) {

        if (reviewRepository.findById(reviewDto.getId()).isPresent()){
            throw new CustomException(ErrorCode.REVIEW_FOUND);
        };
        reviewRepository.save(reviewDto.toEntity());
    }


    /**
     * 공지사항 전체 리스트 조회
     * @param pageable 페이징
     * @param searchNoticeCondition 공지사항 검색
     * @param sortNoticeCond 공지사항 정렬
     * @return
     */

    @Transactional(readOnly = true)
    public Page<ResponseNoticeDto> noticelist(Pageable pageable, SearchNoticeCondition searchNoticeCondition, String sortNoticeCond) {

        return boardQueryRepositoryImpl.findAll(pageable,sortNoticeCond,searchNoticeCondition).map(Notice::noticeList);
    }

    /**
     * 공지사항 단건 조회
     * @param noticeId
     * @return
     */
    @Transactional(readOnly = true)
    public ResponseNoticeDto findOneNotice(Long noticeId) {
        Optional<Notice> findNotice= noticeRepository.findById(noticeId);
        return findNotice.map(notice -> ResponseNoticeDto.getNoticeList(notice)).orElseThrow(()-> new CustomException(ErrorCode.NOTICE_NOT_FOUND));
    }
    /**
     * 공지사항 검색
     * @param pageable 페이징
     * @param searchNoticeCondition 검색명 (관리자 이름, 관리자 이메일, 공지사항 제목)
     * @return
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
     * @param noticeId
     * @return
     */
    @Transactional
    public Long updateNotice(RequestNoticeDto noticeDto, Long noticeId) {
        Member member=memberRepository.findById(noticeDto.getMemberId()).orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        noticeRepository.findById(noticeId).orElseThrow(()-> new CustomException(ErrorCode.NOTICE_NOT_FOUND)).updateNotice(noticeDto,member);

        return noticeDto.getId();

    }

    /**
     * 공지사항 삭제
     * @param noticeId 삭제할 공지사항 id값
     */
    @Transactional
    public void delNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(()-> new CustomException(ErrorCode.NOTICE_NOT_FOUND));
        boardQueryRepositoryImpl.deleteNotice(notice.getId());
    }

    /**
     * 자주묻는 질문 전체 리스트 조회
     * @param pageable 페이징
     * @param searchFaqCondition 검색값
     * @param sortFaqCond 정렬값
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ResponseFaqDto> faqlist(Pageable pageable, SearchFaqCondition searchFaqCondition, String sortFaqCond) {

        return boardQueryRepositoryImpl.findAll(pageable,sortFaqCond,searchFaqCondition).map(Faq::faqList);

    }

    /**
     * 자주묻는 질문 단건 조회
     * @param faqId
     * @return
     */
    @Transactional(readOnly = true)
    public ResponseFaqDto findOneFaq(Long faqId) {
        Optional<Faq> findFaq= faqRepository.findById(faqId);
        return findFaq.map(faq -> ResponseFaqDto.getFaqList(faq)).orElseThrow(()-> new CustomException(ErrorCode.FAQ_NOT_FOUND));
    }

    /**
     * 자주 묻는 질문 검색
     * @param of
     * @param searchFaqCondition
     * @return
     */
    @Transactional
    public Page<ResponseFaqDto> searchFaqList(Pageable pageable, SearchFaqCondition searchFaqCond) {

        Page<Faq> faqList=boardQueryRepositoryImpl.searchFaqList(pageable,searchFaqCond);
        return new PageImpl<>(faqList.stream().map(faq -> ResponseFaqDto.getFaqList(faq)).collect(Collectors.toList()));
    }

    /**
     * 자주 묻는 질문 답변 추가
     * @param faqDto
     * @param faqId
     * @return
     */
    @Transactional
    public String faqAddAnswer(RequestFaqDto faqDto, Long faqId) {
        faqRepository.findById(faqId).orElseThrow(()-> new CustomException(ErrorCode.FAQ_NOT_FOUND)).addFaqAnswer(faqDto);
        return faqDto.getAnswer();
    }

    /**
     * 자주 묻는 질문 수정
     * @param faqDto
     * @param faqId
     * @return
     */
    @Transactional
    public Long updateFaq(RequestFaqDto faqDto, Long faqId) {
        Member member=memberRepository.findById(faqDto.getMemberId()).orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        FaqCategory faqCategory= faqCategoryRepository.findById(faqDto.getCategory()).orElseThrow(()-> new CustomException(ErrorCode.FAQCATEGORY_NOT_FOUND));

        faqRepository.findById(faqId).orElseThrow(()-> new CustomException(ErrorCode.FAQ_NOT_FOUND)).updateFaq(faqDto,member,faqCategory);

        return faqDto.getId();
    }

    /**
     * 자주 묻는 질문 삭제
     * @param faqId
     */
    @Transactional
    public void delFaq(Long faqId) {
        Faq faq = faqRepository.findById(faqId).orElseThrow(()->new CustomException(ErrorCode.FAQ_NOT_FOUND));
        boardQueryRepositoryImpl.deleteFaq(faq.getId());
    }

}
