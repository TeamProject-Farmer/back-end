package com.farmer.backend.api.service.review;

import com.farmer.backend.api.controller.user.review.request.RequestReviewStarDto;
import com.farmer.backend.api.controller.user.review.response.ResponseBestReviewListDto;
import com.farmer.backend.api.controller.user.review.response.ResponseProductReviewListDto;
import com.farmer.backend.api.controller.user.review.response.ResponseReviewStarDto;
import com.farmer.backend.domain.product.Product;
import com.farmer.backend.domain.product.ProductRepository;
import com.farmer.backend.domain.product.productreview.ProductReviewQueryRepositoryImpl;
import com.farmer.backend.domain.product.productreview.ProductReviewRepository;
import com.farmer.backend.domain.product.productreview.ProductReviews;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
class ReviewServiceTest {

    @Autowired
    ProductReviewQueryRepositoryImpl productReviewQueryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductReviewRepository productReviewRepository;

    @Test
    @DisplayName("베스트 리튜 전체 리스트 조회")
    void bestReviewList() {

        List<ResponseBestReviewListDto> bestReviewList = productReviewQueryRepository.bestReviewList();

        for (ResponseBestReviewListDto bestReview : bestReviewList) {
            log.info("Best Review : {} -> {}", bestReview.getMemberNickName(), bestReview.getContent());
            log.info("like counts : {} ", bestReview.getLikeCount());
        }

    }

    @Test
    @DisplayName("상품별 리뷰 리스트")
    void productReviewList() {
        Long productId = 5L;
        Pageable pageable = PageRequest.of(1,3);

        Page<ResponseProductReviewListDto> productReviewListDtoPage
                = productReviewQueryRepository.productReviewList(pageable, "best", 5, productId);

        for (ResponseProductReviewListDto productReviewListDto : productReviewListDtoPage) {

            log.info("Review : {}, {} ", productReviewListDto.getMemberNickname(), productReviewListDto.getProductName());
            log.info("  {} , {}  ", productReviewListDto.getLikeCount(), productReviewListDto.getContent());

        }
    }

    @Test
    @DisplayName("상품별 리뷰 별점 리스트")
    void productReviewAverage() {
        Long productId = 8L;
        double average = 0;
        ArrayList<Long> reviewCount = new ArrayList<>(Arrays.asList(0L, 0L, 0L, 0L,0L));

        Product product = productRepository.findById(productId).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        RequestReviewStarDto reviewStar = productReviewQueryRepository.fiveStars(productId);

        ResponseReviewStarDto responseReviewStarDto = new ResponseReviewStarDto(reviewStar.getAverageStarRating(), reviewStar.getFiveStar(),
                reviewStar.getFourStar(), reviewStar.getThreeStar(), reviewStar.getTwoStar(), reviewStar.getOneStar());

        List<ProductReviews> productReviews= productReviewRepository.findByOrderProductProductId(productId);

        for(ProductReviews review : productReviews){
            int reviewStarScore =  review.getFiveStarRating();
            average+=reviewStarScore;

            Long tmp = reviewCount.get(reviewStarScore-1);
            reviewCount.set(reviewStarScore-1,tmp+1);
        }

        average= Math.round((average/productReviews.size())*10)/10.0;
        log.info(String.valueOf(average));

        assertThat(responseReviewStarDto.getAverageStarRating()).isEqualTo(average);
        assertThat(responseReviewStarDto.getFiveStar()).isEqualTo(reviewCount.get(4));
        assertThat(responseReviewStarDto.getFourStar()).isEqualTo(reviewCount.get(3));
        assertThat(responseReviewStarDto.getThreeStar()).isEqualTo(reviewCount.get(2));
        assertThat(responseReviewStarDto.getTwoStar()).isEqualTo(reviewCount.get(1));
        assertThat(responseReviewStarDto.getOneStar()).isEqualTo(reviewCount.get(0));

        log.info("<상품명 : {}> ", product.getName());
        log.info("별점 평균 : {}", responseReviewStarDto.getAverageStarRating());
        log.info(" 5점 : {}개 , 4점 : {}개, 3점 : {}개, 2점 : {}개, 1점 : {}개", responseReviewStarDto.getFiveStar(), responseReviewStarDto.getFourStar(),
                responseReviewStarDto.getThreeStar(), responseReviewStarDto.getTwoStar(), responseReviewStarDto.getOneStar());

    }
}