package com.farmer.backend.domain.product.productreview;

import com.farmer.backend.api.controller.SortOrderCondition;
import com.farmer.backend.api.controller.review.request.SearchProductReviewCondition;
import com.farmer.backend.api.controller.review.response.ResponseBestReviewListDto;
import com.farmer.backend.api.controller.review.response.ResponseProductReviewListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductReviewQueryRepository {
    List<ResponseBestReviewListDto> bestReviewList();

    Page<ResponseProductReviewListDto> productReviewList(Pageable pageable, String sortOrderCondition,
                                                         SearchProductReviewCondition searchCondition, Long productId);
}
