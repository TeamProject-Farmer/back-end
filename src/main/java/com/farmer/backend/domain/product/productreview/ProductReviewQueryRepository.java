package com.farmer.backend.domain.product.productreview;

import com.farmer.backend.api.controller.user.review.request.RequestReviewStarDto;
import com.farmer.backend.api.controller.user.review.response.ResponseBestReviewListDto;
import com.farmer.backend.api.controller.user.review.response.ResponseProductReviewListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductReviewQueryRepository {
    List<ResponseBestReviewListDto> bestReviewList();

    Page<ResponseProductReviewListDto> productReviewList(Pageable pageable, String sortOrderCond,
                                                         Integer reviewCond, Long productId);


    RequestReviewStarDto fiveStars(Long productId);

    List<String> productReviewImg(Long productId);

}
