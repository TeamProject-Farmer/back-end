package com.farmer.backend.domain.product.productreview;

import com.farmer.backend.api.controller.review.response.ResponseProductReviewListDto;

import java.util.List;

public interface ProductReviewQueryRepository {
    List<ResponseProductReviewListDto> bestReviewList();
}
