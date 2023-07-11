package com.farmer.backend.api.service.review;

import com.farmer.backend.api.controller.review.request.SearchProductReviewCondition;
import com.farmer.backend.api.controller.review.response.ResponseBestReviewListDto;
import com.farmer.backend.api.controller.review.response.ResponseProductReviewListDto;
import com.farmer.backend.domain.product.productreview.ProductReviewQueryRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
@Slf4j
class ReviewServiceTest {

    @Autowired
    ProductReviewQueryRepositoryImpl productReviewQueryRepository;
    @Test
    @DisplayName("베스트 리튜 전체 리스트 조회")
    void bestReviewList() {

        List<ResponseBestReviewListDto> bestReviewList = productReviewQueryRepository.bestReviewList();

        for(ResponseBestReviewListDto bestReview : bestReviewList){
            log.info("Best Review : {} -> {}", bestReview.getMemberNickName(),bestReview.getContent());
            log.info("like counts : {} ",bestReview.getLikeCount());
        }

    }

    @Test
    @DisplayName("상품별 리뷰 리스트")
    void productReviewList() {
        Long productId = 5L;
        Pageable pageable = PageRequest.of(0, 10);
        SearchProductReviewCondition searchProductReviewCondition = new SearchProductReviewCondition();

        Page<ResponseProductReviewListDto> productReviewListDtoPage
                = productReviewQueryRepository.productReviewList(pageable, "best", searchProductReviewCondition, productId);

        for (ResponseProductReviewListDto productReviewListDto : productReviewListDtoPage) {

            log.info("Review : {}, {} ", productReviewListDto.getMemberNickname(), productReviewListDto.getProductName());
            log.info("  {} , {}  ", productReviewListDto.getLikeCount(), productReviewListDto.getContent());

        }
    }
}