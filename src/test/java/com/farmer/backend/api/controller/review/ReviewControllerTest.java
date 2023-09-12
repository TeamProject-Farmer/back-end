package com.farmer.backend.api.controller.review;

import com.farmer.backend.api.controller.user.review.ReviewController;
import com.farmer.backend.api.controller.user.review.response.ResponseBestReviewListDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
@Slf4j
class ReviewControllerTest {

    @Autowired
    ReviewController reviewController;
    @Test
    void bestReviewList() {

        List<ResponseBestReviewListDto> bestReviewList = reviewController.bestReviewList();

        for(ResponseBestReviewListDto bestReview : bestReviewList){
            log.info("best Review : {} , {}" ,bestReview.getMemberNickName(),bestReview.getContent() );
            log.info("like count : {}",bestReview.getLikeCount());
        }
    }
}