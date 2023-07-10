package com.farmer.backend.api.service.review;

import com.farmer.backend.api.controller.review.response.ResponseProductReviewListDto;
import com.farmer.backend.domain.product.productreview.ProductReviewQueryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ProductReviewQueryRepositoryImpl productReviewQueryRepositoryImpl;

    /**
     * 베스트 리뷰 전체 리스트
     * @return
     */
    @Transactional(readOnly = true)
    public List<ResponseProductReviewListDto> bestReviewList() {

        return productReviewQueryRepositoryImpl.bestReviewList();
    }
}
