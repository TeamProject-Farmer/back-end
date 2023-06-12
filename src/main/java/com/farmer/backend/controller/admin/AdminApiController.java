package com.farmer.backend.controller.admin;

import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.dto.admin.board.*;
import com.farmer.backend.dto.admin.member.RequestMemberDto;
import com.farmer.backend.dto.admin.member.ResponseMemberDto;
import com.farmer.backend.dto.admin.member.SearchMemberCondition;
import com.farmer.backend.dto.admin.SortOrderCondition;
import com.farmer.backend.dto.admin.orders.ResponseOrdersDto;
import com.farmer.backend.dto.admin.orders.SearchOrdersCondition;
import com.farmer.backend.dto.admin.product.*;
import com.farmer.backend.entity.OrderDetail;
import com.farmer.backend.entity.OrderStatus;
import com.farmer.backend.paging.PageRequest;
import com.farmer.backend.service.MemberService;
import com.farmer.backend.service.OrderService;
import com.farmer.backend.service.ProductService;
import com.farmer.backend.service.admin.BoardService;
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
    @GetMapping("/order/{orderId}")
    public List<Object> orderInfo(@PathVariable Long orderId) {
        return orderService.orderInfo(orderId);
    }



    /**
     * 관리자 게시판 관리 페이지(Q&A 전체 리스트)
     */
    @ApiDocumentResponse
    @Operation(summary = "게시판 관리 Q&A 리스트", description = "Q&A 리스트를 출력합니다.")
    @GetMapping("/board/qna")
    public Page<ResponseBoardQnADto> boardQnAList(PageRequest pageRequest,
                                                  SortOrderQnaCondition sortOrderQnaCondition,
                                                  SearchQnaCondition searchQnaCondition){

        Page<ResponseBoardQnADto> qnaList = boardService.qnaList(
                pageRequest.of(),
                sortOrderQnaCondition.getFieldName(),
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
    public String addAnswer(@ModelAttribute RequestBoardQnADto answerDto, @PathVariable("id") Long qnaId){
        return boardService.addAns(answerDto,qnaId);
    }

    /**
     * 관리자 게시판 Q&A (Q&A 수정)
     */
    @ApiDocumentResponse
    @Operation(summary = "Q&A 수정", description = "Q&A를 수정합니다.")
    @PostMapping("/board/qna/update/{id}")
    public Long updateQnA(@ModelAttribute RequestBoardQnADto qnaDto, @PathVariable("id") Long qna_id) {
        return boardService.updateQnA(qnaDto,qna_id);

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
                                                        SortOrderReviewCondition sortOrderReviewCondition,
                                                        SearchReviewCondition searchReviewCondition){
        Page<ResponseBoardReviewDto> reviewList = boardService.reviewList(
                pageRequest.of(),
                sortOrderReviewCondition.getFieldName(),
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
    @ApiDocumentResponse
    @Operation(summary = "리뷰 검색 리스트", description = "리뷰 검색 리스트를 출력합니다.")
    @GetMapping("/board/review/search")
    public Page<ResponseBoardReviewDto> searchReviewList(PageRequest pageRequest, SearchReviewCondition searchReviewCondition) {
        Page<ResponseBoardReviewDto> reviewList = boardService.searchReviewList(pageRequest.of(), searchReviewCondition);
        return ResponseEntity.ok(reviewList).getBody();
    }

    /**
     * 관리자 게시판 Review (추가)
     */
    @ApiDocumentResponse
    @Operation(summary = "Review 추가" , description = "Review 를 추가합니다.")
    @PostMapping("board/review/add")
    public void addReview(RequestBoardReviewDto reviewDto){
        boardService.addReview(reviewDto);
    }


    /**
     * 관리자 게시판 Review (수정)
     */
    @ApiDocumentResponse
    @Operation(summary = "Review 수정", description = "Review 를 수정합니다.")
    @PostMapping("/board/review/update/{id}")
    public Long updateReview(@ModelAttribute RequestBoardReviewDto reviewDto, @PathVariable("id") Long reviewId) {
        return boardService.updateReview(reviewDto,reviewId);

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

}
