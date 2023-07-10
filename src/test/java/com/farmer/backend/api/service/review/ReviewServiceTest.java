package com.farmer.backend.api.service.review;

import com.farmer.backend.api.controller.review.response.ResponseBestReviewListDto;
import com.farmer.backend.domain.product.productreview.ProductReviewQueryRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}