package com.farmer.backend.api.service.review;

import com.farmer.backend.api.controller.SortOrderCondition;
import com.farmer.backend.api.controller.review.request.SearchProductReviewCondition;
import com.farmer.backend.api.controller.review.response.ResponseBestReviewListDto;
import com.farmer.backend.api.controller.review.response.ResponseProductReviewListDto;
import com.farmer.backend.domain.product.productreview.ProductReviewQueryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public List<ResponseBestReviewListDto> bestReviewList() {

        return productReviewQueryRepositoryImpl.bestReviewList();
    }

    /**
     * 상품별 리뷰 전체 리스트
     * @param pageable 페이징
     * @param sortOrderCond 특정 별점대 ex)star=5
     * @param searchCond 베스트순, 최신순 정렬 ex) sortOrderCond = best , sortOrderCond = recent
     * @param productId 상품 ID 값
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ResponseProductReviewListDto> productReviewList(Pageable pageable, String sortOrderCond, SearchProductReviewCondition searchCond, Long productId) {
        Page<ResponseProductReviewListDto> productReviewList
                = productReviewQueryRepositoryImpl.productReviewList(pageable,sortOrderCond,searchCond,productId);

        return productReviewList;
    }
}
