package com.farmer.backend.api.controller.review;

import com.farmer.backend.api.controller.review.request.RequestReviewWriteDto;
import com.farmer.backend.api.controller.review.response.ResponseBestReviewListDto;
import com.farmer.backend.api.controller.review.response.ResponseProductReviewListDto;
import com.farmer.backend.api.controller.review.response.ResponseReviewStarDto;
import com.farmer.backend.api.service.review.ReviewService;
import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.login.general.MemberAdapter;
import com.farmer.backend.paging.PageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
@Tag(name = "ReviewController", description = "리뷰 도메인 API")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 베스트 리뷰 전체 출력
     */
    @ApiDocumentResponse
    @Operation(summary = "베스트 리뷰 리스트 전체 츌력" ,description = "메인 페이지의 베스트 리뷰를 전체 출력합니다.")
    @GetMapping("/main/review")
    public List<ResponseBestReviewListDto> bestReviewList(){

        return reviewService.bestReviewList();
    }

    /**
     * 상품별 리뷰 평점 조회
     */
    @ApiDocumentResponse
    @Operation(summary = "상품별 리뷰 평점 출력", description = "상품별 리뷰 평점을 출력합니다.")
    @GetMapping("/main/review/star/{productId}")
    public ResponseReviewStarDto reviewAverage(@PathVariable(name = "productId") Long productId){
        return reviewService.reviewAverage(productId);
    }

    /**
     * 상품별 리뷰 출력
     */
    @ApiDocumentResponse
    @Operation(summary = "상품별 리뷰 출력", description = "상품별 리뷰를 출력합니다.")
    @GetMapping("/main/review/{productId}")
    public Page<ResponseProductReviewListDto> productReviewList(PageRequest pageRequest,
                                                                String sortOrderCond,
                                                                Integer reviewCond,
                                                                @PathVariable(name = "productId") Long productId){
        pageRequest.setSize(3);
        return reviewService.productReviewList(pageRequest.of(),sortOrderCond,reviewCond,productId);
    }

    /**
     * 상품별 사진 리뷰
     */
    @ApiDocumentResponse
    @Operation(summary = "상품별 사진 리뷰" ,description = "상품별 사진 리뷰를 출력합니다. ")
    @GetMapping("/main/review/img/{productId}")
    public List<String> productReviewImg(@PathVariable(name = "productId") Long productId){
        return reviewService.productReviewImg(productId);
    }

    /**
     * 리뷰 작성
     */
//    @ApiDocumentResponse
//    @Operation(summary = "상품 리뷰 작성", description = "상품 리뷰를 작성합니다.")
//    @PostMapping("/member/review/write/{productId}")
//    public ResponseEntity<String> productReviewWrite(@AuthenticationPrincipal MemberAdapter memberAdapter,
//                                                     @PathVariable(name="productId") Long productId,
//                                                     RequestReviewWriteDto requestReviewWriteDto){
//
//        String memberEmail = memberAdapter.getMember().getEmail();
//        reviewService.reviewWrite(memberEmail,productId,requestReviewWriteDto);
//
//        return new ResponseEntity<>("ok", HttpStatus.OK);
//
//    }

}