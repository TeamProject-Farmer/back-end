package com.farmer.backend.api.controller;

import com.farmer.backend.api.controller.options.request.RequestOptionDto;
import com.farmer.backend.api.controller.order.request.SearchOrdersCondition;
import com.farmer.backend.api.controller.order.response.ResponseOrdersDto;
import com.farmer.backend.api.controller.product.request.RequestProductDto;
import com.farmer.backend.api.controller.product.response.ResponseProductDto;
import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.api.controller.faq.request.RequestFaqDto;
import com.farmer.backend.api.controller.faq.response.ResponseFaqDto;
import com.farmer.backend.api.controller.faq.request.SearchFaqCondition;
import com.farmer.backend.api.controller.notice.request.RequestNoticeDto;
import com.farmer.backend.api.controller.notice.response.ResponseNoticeDto;
import com.farmer.backend.api.controller.notice.request.SearchNoticeCondition;
import com.farmer.backend.api.controller.qna.request.RequestBoardQnADto;
import com.farmer.backend.api.controller.qna.response.ResponseBoardQnADto;
import com.farmer.backend.api.controller.qna.request.SearchQnaCondition;
import com.farmer.backend.api.controller.review.response.ResponseBoardReviewDto;
import com.farmer.backend.api.controller.review.request.SearchReviewCondition;
import com.farmer.backend.api.controller.member.request.RequestMemberDto;
import com.farmer.backend.api.controller.member.response.ResponseMemberDto;
import com.farmer.backend.api.controller.member.request.SearchMemberCondition;
import com.farmer.backend.api.controller.productcategory.request.RequestProductCategoryDto;
import com.farmer.backend.api.controller.productcategory.response.ResponseProductCategoryListDto;
import com.farmer.backend.api.controller.coupon.request.RequestCouponDetailDto;
import com.farmer.backend.api.controller.coupon.request.RequestCouponDto;
import com.farmer.backend.api.controller.coupon.response.ResponseCouponDetailDto;
import com.farmer.backend.domain.product.productcategory.ProductCategory;
import com.farmer.backend.paging.PageRequest;
import com.farmer.backend.api.service.member.MemberService;
import com.farmer.backend.api.service.order.OrderService;
import com.farmer.backend.api.service.product.ProductService;
import com.farmer.backend.api.service.SettingsService;
import com.farmer.backend.api.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Tag(name = "AdminController", description = "백오피스 관리자 페이지 API")
public class AdminApiController {

    private final MemberService memberService;
    private final ProductService productService;
    private final BoardService boardService;
    private final OrderService orderService;
    private final SettingsService settingsService;


    /**
     * 계정 관리 페이지(관리자 권한 계정 리스트)
     * 검색, 정렬
     */
    @ApiDocumentResponse
    @Operation(summary = "관리자 권한 계정 리스트", description = "관리자 권한을 가진 유저들을 출력합니다.")
    @GetMapping("/account")
    public Page<ResponseMemberDto> managerList(PageRequest pageRequest,
                                               SortOrderCondition sortOrderMemberCondition,
                                               SearchMemberCondition searchMemberCondition) {

        Page<ResponseMemberDto> managerList = memberService.managerList(
                pageRequest.of(),
                sortOrderMemberCondition.getFieldName(),
                searchMemberCondition
        );

        return ResponseEntity.ok(managerList).getBody();
    }

    /**
     * 계정 관리 페이지(관리자 단건 조회)
     */
    @ApiDocumentResponse
    @Operation(summary = "관리자 단건 조회", description = "특정 관리자 정보를 열람합니다.")
    @GetMapping("/account/managers/{memberId}")
    public ResponseEntity<ResponseMemberDto> findManager(@PathVariable Long memberId) {
        ResponseMemberDto oneMember = memberService.findOneMember(memberId);
        return new ResponseEntity<>(oneMember, HttpStatus.OK);
    }

    /**
     * 계정 관리 페이지(관리자 권한 계정 검색)
     */
    @ApiDocumentResponse
    @Operation(summary = "관리자 권한 검색 리스트", description = "관리자 권한을 가진 유저들을 검색합니다.")
    @GetMapping("/account/search")
    public Page<ResponseMemberDto> searchManagerList(PageRequest pageRequest, SearchMemberCondition searchMemberCondition) {
        Page<ResponseMemberDto> searchList = memberService.searchManagerList(pageRequest.of(), searchMemberCondition);
        return ResponseEntity.ok(searchList).getBody();
    }

    /**
     * 계정 관리 페이지(관리자 권한 계정 수정)
     */
    @ApiDocumentResponse
    @Operation(summary = "관리자 권한 계정 수정", description = "관리자 권한을 가진 유저들을 수정합니다.")
    @PostMapping("/account/managers-update")
    public Long updateManager(@Valid @ModelAttribute RequestMemberDto memberDto) {
        return memberService.updateMember(memberDto);
    }


    /**
     * 회원 관리 페이지(회원 전체 리스트)
     * 검색, 정렬
     */
    @ApiDocumentResponse
    @Operation(summary = "회원 전체 리스트", description = "회원 전체 리스트를 출력합니다.")
    @GetMapping("/member-list")
    public Page<ResponseMemberDto> memberList(PageRequest pageRequest,
                                              SortOrderCondition sortOrderMemberCondition,
                                              SearchMemberCondition searchMemberCondition) {

        Page<ResponseMemberDto> memberList = memberService.memberList(
                pageRequest.of(),
                sortOrderMemberCondition.getFieldName(),
                searchMemberCondition
        );
        return ResponseEntity.ok(memberList).getBody();
    }

    /**
     * 회원 관리 페이지(회원 단건 조회)
     */
    @ApiDocumentResponse
    @Operation(summary = "회원 단건 조회", description = "특정 회원 정보를 열람합니다.")
    @GetMapping("/members/{memberId}")
    public ResponseEntity<ResponseMemberDto> findMember(@PathVariable Long memberId) {
        ResponseMemberDto oneMember = memberService.findOneMember(memberId);
        return new ResponseEntity<>(oneMember, HttpStatus.OK);
    }

    /**
     * 회원 관리 페이지(회원 수정)
     */
    @ApiDocumentResponse
    @Operation(summary = "회원 수정", description = "회원 정보를 수정합니다.")
    @PostMapping("/members/update")
    public Long updateMember(@ModelAttribute RequestMemberDto memberDto) {
        return memberService.updateMember(memberDto);
    }

    /**
     * 회원 관리 페이지(회원 삭제)
     */
    @ApiDocumentResponse
    @Operation(summary = "회원 삭제", description = "회원을 삭제합니다.")
    @PostMapping("/members/delete")
    public void deleteMember(@RequestParam(value = "member") Long memberIds[]) {
        memberService.deleteMember(memberIds);
    }

    /**
     * 회원 관리 페이지(회원 검색(이름, 아이디))
     */
    @ApiDocumentResponse
    @Operation(summary = "회원 검색 리스트", description = "회원 검색 리스트를 출력합니다.")
    @GetMapping("/member-list/search")
    public Page<ResponseMemberDto> searchMemberList(PageRequest pageRequest, SearchMemberCondition searchMemberCondition) {
        Page<ResponseMemberDto> searchList = memberService.searchMemberList(pageRequest.of(), searchMemberCondition);
        return ResponseEntity.ok(searchList).getBody();
    }

    /**
     * 상품 관리 페이지
     * 검색, 정렬
     */
    @ApiDocumentResponse
    @Operation(summary = "상품 전체 리스트", description = "상품 전체 리스트를 출력합니다.")
    @GetMapping("/product-list")
    public Page<ResponseProductDto> productList(PageRequest pageRequest, String productName, SortOrderCondition orderCondition) {
        return productService.productList(pageRequest.of(), productName, orderCondition.getFieldName());
    }

    /**
     * 상품 관리 페이지(상품 단건 조회)
     */
    @ApiDocumentResponse
    @Operation(summary = "상품 단건 조회", description = "선택한 상품 한건을 조회합니다.")
    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseProductDto> findProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.productOne(productId));
    }

    /**
     * 상품 관리 페이지(상품 등록 버튼)
     */
    @ApiDocumentResponse
    @Operation(summary = "상품 등록 버튼", description = "카테고리 리스트를 출력합니다.")
    @GetMapping("/product/create-form")
    public Map<Long, String> registerProductForm() {
        return productService.getCategoryList();
    }

    /**
     * 상품 관리 페이지(옵션 등록)
     */
    @ApiDocumentResponse
    @Operation(summary = "옵션 등록", description = "옵션 한 건을 등록합니다.")
    @PostMapping("/product/add-option")
    public void addOption(@ModelAttribute RequestOptionDto optionDto) {
        productService.addOptionAction(optionDto);
    }

    @ApiDocumentResponse
    @Operation(summary = "옵션 수정 폼", description = "옵션 수정 폼으로 이동합니다.")
    @PostMapping("/product/{optionId}/option")
    public void updateOption(@PathVariable Long optionId, RequestOptionDto optionDto) {
        productService.updateActionOption(optionId, optionDto);
    }

    /**
     * 상품 관리 페이지(옵션 삭제)
     */
    @ApiDocumentResponse
    @Operation(summary = "옵션 삭제", description = "옵션 한 건을 삭제합니다.")
    @PostMapping("/product/option-remove/{optionId}")
    public void removeOption(@PathVariable Long optionId) {
        productService.removeOption(optionId);
    }

    /**
     * 상품 관리 페이지(상품 등록 기능 수행)
     */
    @ApiDocumentResponse
    @Operation(summary = "상품 등록", description = "상품 한 건을 등록합니다.")
    @PostMapping("/product/create")
    public void registerActionProduct(@ModelAttribute RequestProductDto productDto, BindingResult bindingResult) {
        log.info("productDto={}", productDto.toString());
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(r -> log.error(r.getField()));
        }
        productService.registerProduct(productDto);
    }

    /**
     * 상품 관리 페이지(상품 단건 수정)
     */
    @ApiDocumentResponse
    @Operation(summary = "상품 수정", description = "상품 한 건을 수정합니다.")
    @PostMapping("/product/{productId}")
    public void updateProduct(@PathVariable Long productId, RequestProductDto productDto) {
        productService.updateActionProduct(productId, productDto);
    }

    /**
     * 상품 관리 페이지(상품 삭제)
     */
    @ApiDocumentResponse
    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    @PostMapping("/product/delete")
    public void deleteProduct(@RequestParam(value = "product") Long productIds[]) {
        productService.deleteProduct(productIds);
    }

    /**
     * 상품 관리 페이지(상품 검색)
     */
    @ApiDocumentResponse
    @Operation(summary = "상품 검색", description = "상품 검색 리스트를 출력합니다.")
    @GetMapping("/product/search")
    public Page<ResponseProductDto> searchProductList(PageRequest pageRequest, String productName) {
        return productService.searchActionProductList(pageRequest.of(), productName);
    }

    /**
     * 주문 관리 페이지
     * (검색, 정렬)
     */
    @ApiDocumentResponse
    @Operation(summary = "주문 전체 리스트", description = "주문 전체 리스트를 출력합니다.")
    @GetMapping("/order-list")
    public Page<ResponseOrdersDto> orderList(PageRequest pageRequest, SearchOrdersCondition searchCond, SortOrderCondition sortCond) {
        return orderService.orderList(pageRequest.of(), searchCond, sortCond.getFieldName());
    }

    /**
     * 주문 관리 페이지(주문 단건 조회)
     */
    @ApiDocumentResponse
    @Operation(summary = "주문 단건 조회", description = "주문 한건을 조회합니다")
    @GetMapping("/order/{orderId}")
    public List<Object> orderInfo(@PathVariable Long orderId) {
        return orderService.orderInfo(orderId);
    }

    @ApiDocumentResponse
    @Operation(summary = "주문 상태 변경", description = "주문 한 건 상태를 변경합니다.")
    @PostMapping("/order-update/{orderId}")
    public ResponseEntity orderUpdate(@PathVariable Long orderId, String orderStatus) {
        orderService.orderStatusUpdate(orderId, orderStatus);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 주문 관리 페이지(주문 삭제)
     */
    @ApiDocumentResponse
    @Operation(summary = "주문 삭제", description = "주문 한건을 삭제합니다")
    @PostMapping("/order/delete/{orderId}")
    public ResponseEntity orderRemove(@PathVariable Long orderId) {
        orderService.orderRemove(orderId);
        return ResponseEntity.ok(orderId);
    }

    /**
     * 기타 설정 페이지(쿠폰, 카테고리)
     */
    @ApiDocumentResponse
    @Operation(summary = "기타 설정 페이지(쿠폰 관리, 카테고리 관리)", description = "쿠폰 리스트, 카테고리 리스트 출력합니다.")
    @GetMapping("/settings")
    public List<Object> settings() {
        return settingsService.settingsList();
    }

    /**
     * 기타 설정 페이지(쿠폰 조회)
     */
    @ApiDocumentResponse
    @Operation(summary = "쿠폰 조회", description = "쿠폰 한장의 정보를 조회합니다.")
    @GetMapping("/settings/coupon/{couponId}")
    public ResponseEntity<ResponseCouponDetailDto> couponDetail(@PathVariable Long couponId) {
        return ResponseEntity.ok(settingsService.findByCoupon(couponId));
    }

    /**
     * 기타 설정 페이지(쿠폰 고유 번호 생성)
     */
    @ApiDocumentResponse
    @Operation(summary = "쿠폰 시리얼번호 생성", description = "쿠폰 고유의 일련번호를 생성합니다.")
    @GetMapping("/settings/create-serial-number")
    public String createSerialNumber() {
        return settingsService.createSerialNumber();
    }

    /**
     * 기타 설정 페이지(쿠폰 생성)
     */
    @ApiDocumentResponse
    @Operation(summary = "쿠폰 생성", description = "쿠폰 한장을 생성합니다.")
    @PostMapping("/settings/coupon")
    public ResponseEntity createCoupon(@ModelAttribute RequestCouponDto requestCouponDto) {
        return ResponseEntity.ok(settingsService.createCouponAction(requestCouponDto));
    }

    /**
     * 기타 설정 페이지(쿠폰 삭제)
     */
    @ApiDocumentResponse
    @Operation(summary = "쿠폰 삭제", description = "쿠폰 한장 또는 여러장을 삭제합니다.")
    @PostMapping("/settings/remove-coupon/{couponIds}")
    public ResponseEntity removeCoupon(@PathVariable Long[] couponIds) {
        settingsService.removeCouponAction(couponIds);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 기타 설정 페이지(쿠폰 수정)
     */
    @ApiDocumentResponse
    @Operation(summary = "쿠폰 수정", description = "쿠폰 한장의 정보를 수정합니다")
    @PostMapping("/settings/coupon-update/{couponId}")
    public ResponseEntity updateCoupon(@ModelAttribute RequestCouponDetailDto couponDetailDto, @PathVariable Long couponId) {
        settingsService.updateCouponAction(couponDetailDto, couponId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 기타 설정 페이지(상품 카테고리 조회)
     */
    @ApiDocumentResponse
    @Operation(summary = "상품 카테고리 조회", description = "상품 카테고리 한 건을 조회합니다.")
    @GetMapping("/settings/product-category/{productCategoryId}")
    public ResponseEntity<ResponseProductCategoryListDto> productCategoryDetail(@PathVariable Long productCategoryId) {
        return ResponseEntity.ok(settingsService.findProductCategory(productCategoryId));
    }

    @ApiDocumentResponse
    @Operation(summary = "상품 카테고리 생성", description = "상품 카테고리를 생성합니다.")
    @PostMapping("/settings/product-category")
    public ResponseEntity createProductCategory(@ModelAttribute RequestProductCategoryDto productCategoryDto) {
        ProductCategory productCategory = settingsService.createProductCategoryAction(productCategoryDto);
        if (Objects.isNull(productCategory)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 관리자 게시판 관리 페이지(Q&A 전체 리스트)
     */
    @ApiDocumentResponse
    @Operation(summary = "게시판 관리 Q&A 리스트", description = "Q&A 리스트를 출력합니다.")
    @GetMapping("/board/qna")
    public Page<ResponseBoardQnADto> boardQnAList(PageRequest pageRequest,
                                                  SortOrderCondition sortOrderCondition,
                                                  SearchQnaCondition searchQnaCondition){

        Page<ResponseBoardQnADto> qnaList = boardService.qnaList(
                pageRequest.of(),
                sortOrderCondition.getFieldName(),
                searchQnaCondition
        );

        return ResponseEntity.ok(qnaList).getBody();
    }


    /**
     * 관리자 게시판 Q&A (Q&A 단건 조회)
     */
    @ApiDocumentResponse
    @Operation(summary = "Q&A 단건 조회", description = "특정 Q&A를 열람합니다.")
    @GetMapping("/board/qna/{qnaId}")
    public ResponseEntity<ResponseBoardQnADto> findQnA(@PathVariable Long qnaId) {
        ResponseBoardQnADto oneQnA = boardService.findOneQnA(qnaId);
        return new ResponseEntity<>(oneQnA, HttpStatus.OK);
    }

    /**
     * 관리자 게시판 Q&A (Q&A 검색(상품 이름, 회원 Email, 회원 이름))
     */
    @ApiDocumentResponse
    @Operation(summary = "Q&A 검색 리스트", description = "Q&A 검색 리스트를 출력합니다.")
    @GetMapping("/board/qna/search")
    public Page<ResponseBoardQnADto> searchQnAList(PageRequest pageRequest, SearchQnaCondition searchQnaCondition) {
        Page<ResponseBoardQnADto> qnaSearchList = boardService.searchQnAList(pageRequest.of(), searchQnaCondition);
        return ResponseEntity.ok(qnaSearchList).getBody();
    }


    /**
     * 관리자 게시판 Q&A (Answer 추가 -> 추가버튼 클릭 )
     */
    @ApiDocumentResponse
    @Operation(summary = "Q&A ANSWER 추가",description = "특정 Question에 답변을 답니다.")
    @PostMapping("/board/qna/add/{id}")
    public void addAnswer(@ModelAttribute RequestBoardQnADto answerDto, @PathVariable("id") Long qnaId){
        boardService.addAns(answerDto,qnaId);
    }

    /**
     * 관리자 게시판 Q&A (Q&A 답변 수정)
     */
    @ApiDocumentResponse
    @Operation(summary = "Q&A 수정", description = "Q&A를 수정합니다.")
    @PostMapping("/board/qna/update/{id}")
    public void updateQnA(@ModelAttribute RequestBoardQnADto qnaDto, @PathVariable("id") Long qna_id) {
        boardService.updateQnA(qnaDto,qna_id);

    }

    /**
     * 관리자 게시판 Q&A (Q&A 삭제)
     */
    @ApiDocumentResponse
    @Operation(summary = "qna 삭제", description = "QnA를 삭제합니다.")
    @PostMapping("/board/qna/del/{id}")
    public void qnaDel(@PathVariable("id") Long qnaId) {
        boardService.delQna(qnaId);
    }

    /**
     * 관리자 게시판 관리 페이지(review 전체 리스트)
     */
    @ApiDocumentResponse
    @Operation(summary = "게시판 관리 review 리스트", description = "review 리스트를 출력합니다.")
    @GetMapping("/board/review")
    public Page<ResponseBoardReviewDto> boardReviewList(PageRequest pageRequest,
                                                        SortOrderCondition sortOrderCondition,
                                                        SearchReviewCondition searchReviewCondition){
        Page<ResponseBoardReviewDto> reviewList = boardService.reviewList(
                pageRequest.of(),
                sortOrderCondition.getFieldName(),
                searchReviewCondition
        );

        return ResponseEntity.ok(reviewList).getBody();
    }

    /**
     * 관리자 게시판 Review (단건 조회)
     */
    @ApiDocumentResponse
    @Operation(summary = "Review 단건 조회", description = "특정 Review 를 열람합니다.")
    @GetMapping("/board/review/{reviewId}")
    public ResponseEntity<ResponseBoardReviewDto> findReview(@PathVariable Long reviewId) {

        ResponseBoardReviewDto oneReview = boardService.findOneReview(reviewId);
        return new ResponseEntity<>(oneReview, HttpStatus.OK);

    }

    /**
     * 관리자 게시판 Review(리뷰 검색(회원 이름, 회원 아이디))
     */

    @Operation(summary = "리뷰 검색 리스트", description = "리뷰 검색 리스트를 출력합니다.")
    @GetMapping("/board/review/search")
    public Page<ResponseBoardReviewDto> searchReviewList(PageRequest pageRequest, SearchReviewCondition searchReviewCondition) {
        Page<ResponseBoardReviewDto> reviewList = boardService.searchReviewList(pageRequest.of(), searchReviewCondition);
        return ResponseEntity.ok(reviewList).getBody();
    }


    /**
     * 관리자 게시판 Review (삭제)
     */
    @ApiDocumentResponse
    @Operation(summary = "review 삭제", description = "Review 를 삭제합니다.")
    @PostMapping("/board/review/del/{id}")
    public void reviewDel(@PathVariable("id") Long reviewId) {
        boardService.delReview(reviewId);
    }

    /**
     * 관리자 게시판 공지사항 ( 전체 조회 )
     */

    @ApiDocumentResponse
    @Operation(summary = "공지사항 전체 조회", description = "공지사항을 전체 조회합니다.")
    @GetMapping("/board/notice")
    public Page<ResponseNoticeDto> boardNoticeList(PageRequest pageRequest,
                                                   SearchNoticeCondition searchNoticeCondition,
                                                   SortOrderCondition sortOrderCondition){
        Page<ResponseNoticeDto> noticeList=
                boardService.noticeList(pageRequest.of(),searchNoticeCondition,sortOrderCondition.getFieldName());

        return ResponseEntity.ok(noticeList).getBody();
    }

    /**
     * 관리자 게시판 공지사항 ( 공지사항 단건 조회 )
     */
    @ApiDocumentResponse
    @Operation(summary = "공지사항 단건 조회" , description = "특정 공지사항을 열람합니다.")
    @GetMapping("/board/notice/{noticeId}")
    public ResponseEntity<ResponseNoticeDto> findNotice(@PathVariable Long noticeId){

        ResponseNoticeDto oneNotice = boardService.findOneNotice(noticeId);
        return new ResponseEntity<>(oneNotice, HttpStatus.OK);

    }

    /**
     * 관리자 게시판 공지사항 (공지사항 검색(관리자 이름, 관리자 이메일, 공지사항 제목))
     */
    @ApiDocumentResponse
    @Operation(summary = "공지사항 검색",description = "공지사항을 검색합니다.")
    @GetMapping("/board/notice/search")
    public Page<ResponseNoticeDto> searchNoticeList(PageRequest pageRequest, SearchNoticeCondition searchNoticeCondition) {
        Page<ResponseNoticeDto> noticeList = boardService.searchNoticeList(pageRequest.of(), searchNoticeCondition);
        return ResponseEntity.ok(noticeList).getBody();
    }

    /**
     * 관리자 게시판 공지사항 (추가)
     */
    @ApiDocumentResponse
    @Operation(summary = "공지사항 추가", description = "공지사항을 등록합니다.")
    @PostMapping("/board/notice/add")
    public void addNotice(RequestNoticeDto noticeDto){
        boardService.addNotice(noticeDto);
    }

    /**
     * 관리자 게시판 공지사항 (수정)
     */
    @ApiDocumentResponse
    @Operation(summary = "공지사항 수정", description = "공지사항을 수정합니다.")
    @PostMapping("/board/notice/update/{noticeId}")
    public void updateNotice(@ModelAttribute RequestNoticeDto noticeDto ,@PathVariable("noticeId") Long noticeId){

        boardService.updateNotice(noticeDto,noticeId);
    }

    /**
     * 관리자 게시판 공지사항 (삭제)
     */
    @ApiDocumentResponse
    @Operation(summary = "공지사항 삭제",description = "공지사항을 삭제합니다.")
    @PostMapping("/board/notice/del/{noticeId}")
    public void delNotice(@PathVariable("noticeId") Long noticeId) {
        boardService.delNotice(noticeId);
    }

    /**
     * 관리자 게시판 자주 묻는 질문(전체 조회)
     */
    @ApiDocumentResponse
    @Operation(summary = "자주 묻는 질문 조회",description = "자주 묻는 질문을 조회합니다.")
    @GetMapping("/board/faq")
    public Page<ResponseFaqDto> boardFaqList(PageRequest pageRequest,
                                             SearchFaqCondition searchFaqCondition,
                                             SortOrderCondition sortOrderCondition){
        Page<ResponseFaqDto> faqList=
                boardService.faqList(pageRequest.of(),searchFaqCondition,sortOrderCondition.getFieldName());

        return ResponseEntity.ok(faqList).getBody();
    }

    /**
     * 관리자 게시판 자주 묻는 질문 (단건 조회)
     */
    @ApiDocumentResponse
    @Operation(summary = "자주 묻는 질문 단건 조회", description = "특정 자주 묻는 질문을 조회합니다.")
    @GetMapping("/board/faq/{faqId}")
    public ResponseEntity<ResponseFaqDto> findFaq(@PathVariable Long faqId){

        ResponseFaqDto oneFaq = boardService.findOneFaq(faqId);
        return new ResponseEntity<>(oneFaq, HttpStatus.OK);

    }
    /**
     * 자주 묻는 질문 검색 (카테고리 이름, 회원 이름, 회원 아이디)
     */
    @ApiDocumentResponse
    @Operation(summary = "자주 묻는 질문 검색",description = "자주 묻는 질문을 검색합니다.")
    @GetMapping("/board/faq/search")
    public Page<ResponseFaqDto> searchFaqList(PageRequest pageRequest, SearchFaqCondition searchFaqCondition) {
        Page<ResponseFaqDto> faqList = boardService.searchFaqList(pageRequest.of(), searchFaqCondition);
        return ResponseEntity.ok(faqList).getBody();
    }


    /**
     * 관리자 게시판 자주 묻는 질문 (답변 추가)
     */
    @ApiDocumentResponse
    @Operation(summary = "자주 묻는 질문 답변 추가",description = "특정 질문에 답변을 추가합니다.")
    @PostMapping("/board/faq/add/{faqId}")
    public void faqAddAnswer(@ModelAttribute RequestFaqDto faqDto, @PathVariable("faqId") Long faqId){
        boardService.faqAddAnswer(faqDto,faqId);
    }

    /**
     *  관리자 게시판 자주 묻는 질문 (자주 묻는 질문 수정)
     */
    @ApiDocumentResponse
    @Operation(summary = "자주 묻는 질문 수정",description = "자주 묻는 질문을 수정합니다.")
    @PostMapping("/board/faq/update/{faqId}")
    public void updateFaq(@ModelAttribute RequestFaqDto faqDto ,@PathVariable("faqId") Long faqId){

        boardService.updateFaq(faqDto,faqId);
    }

    /**
     * 관리자 게시판 자주 묻는 질문 (자주 묻는 질문 삭제)
     */
    @ApiDocumentResponse
    @Operation(summary = "자주 묻는 질문 삭제",description = "자주 묻는 질문을 삭제합니다.")
    @PostMapping("/board/faq/del/{faqId}")
    public void delFaq(@PathVariable("faqId") Long faqId) {
        boardService.delFaq(faqId);
    }


}
