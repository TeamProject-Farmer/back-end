package com.farmer.backend.api.controller.review;

import com.farmer.backend.api.controller.review.response.ResponseProductReviewListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Slf4j
class ReviewControllerTest {

    @Autowired
    ReviewController reviewController;
    @Test
    void bestReviewList() {

        List<ResponseProductReviewListDto> bestReviewList = reviewController.bestReviewList();

        for(ResponseProductReviewListDto bestReview : bestReviewList){
            log.info("best Review : {} , {}" ,bestReview.getMemberNickName(),bestReview.getContent() );
            log.info("like count : {}",bestReview.getLikeCount());
        }
    }
}