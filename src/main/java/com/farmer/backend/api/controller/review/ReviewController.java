package com.farmer.backend.api.controller.review;

import com.farmer.backend.api.controller.review.response.ResponseProductReviewListDto;
import com.farmer.backend.api.service.review.ReviewService;
import com.farmer.backend.config.ApiDocumentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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

    @ApiDocumentResponse
    @Operation(summary = "리뷰 리스트 전체 조회" ,description = "상품의 리뷰를 전체 조회합니다.")
    @GetMapping("/main/review")
    public List<ResponseProductReviewListDto> bestReviewList(){

        return reviewService.bestReviewList();
    }

}