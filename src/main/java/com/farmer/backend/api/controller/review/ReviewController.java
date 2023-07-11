package com.farmer.backend.api.controller.review;

import com.farmer.backend.api.controller.review.request.SearchProductReviewCondition;
import com.farmer.backend.api.controller.review.response.ResponseBestReviewListDto;
import com.farmer.backend.api.controller.review.response.ResponseProductReviewListDto;
import com.farmer.backend.api.controller.review.response.ResponseReviewStarDto;
import com.farmer.backend.api.service.review.ReviewService;
import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.paging.PageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
@Tag(name = "ReviewController", description = "회원 페이지 API")
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
                                                                SearchProductReviewCondition searchProductReviewCond,
                                                                @PathVariable(name = "productId") Long productId){

        return reviewService.productReviewList(pageRequest.of(),sortOrderCond,searchProductReviewCond,productId);
    }

}