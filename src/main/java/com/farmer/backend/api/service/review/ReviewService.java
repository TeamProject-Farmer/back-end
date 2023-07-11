package com.farmer.backend.api.service.review;

import com.farmer.backend.api.controller.review.request.RequestReviewStarDto;
import com.farmer.backend.api.controller.review.request.SearchProductReviewCondition;
import com.farmer.backend.api.controller.review.response.ResponseBestReviewListDto;
import com.farmer.backend.api.controller.review.response.ResponseProductReviewListDto;
import com.farmer.backend.api.controller.review.response.ResponseReviewStarDto;
import com.farmer.backend.domain.product.Product;
import com.farmer.backend.domain.product.ProductRepository;
import com.farmer.backend.domain.product.productreview.ProductReviewQueryRepositoryImpl;
import com.farmer.backend.domain.product.productreviewstar.ProductReviewAverage;
import com.farmer.backend.domain.product.productreviewstar.ProductReviewAverageRepository;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ProductReviewQueryRepositoryImpl reviewQueryRepositoryImpl;
    private final ProductRepository productRepository;
    private final ProductReviewAverageRepository productReviewAverageRepository;

    /**
     * 베스트 리뷰 전체 리스트
     * @return ResponseBestReviewListDto
     */
    @Transactional(readOnly = true)
    public List<ResponseBestReviewListDto> bestReviewList() {

        return reviewQueryRepositoryImpl.bestReviewList();
    }

    /**
     * 상품별 리뷰 페이지
     * @param pageable 페이징
     * @param sortOrderCond 특정 별점대 ex)star=5
     * @param searchCond 베스트순, 최신순 정렬 ex) sortOrderCond = best , sortOrderCond = recent
     * @param productId 상품 ID 값
     * @return Page<ResponseProductReviewListDto>
     */
    @Transactional(readOnly = true)
    public Page<ResponseProductReviewListDto> productReviewList(Pageable pageable, String sortOrderCond, SearchProductReviewCondition searchCond, Long productId) {



        Page<ResponseProductReviewListDto> productReviewList
                = reviewQueryRepositoryImpl.productReviewList(pageable,sortOrderCond,searchCond,productId);


        return productReviewList;
    }

    /**
     * 상품별 리뷰 별점 리스트
     * @param productId 상품 ID값
     * @return ResponseReviewStarDto
     */
    public ResponseReviewStarDto reviewAverage(Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(()->new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        RequestReviewStarDto reviewStar= reviewQueryRepositoryImpl.fiveStars( productId);

        ProductReviewAverage productReviewAverage = reviewStar.toEntity(product);

        if(productReviewAverageRepository.findByProductId(productId).isPresent()){
            log.info("기존값 업데이트");
            productReviewAverage.updateReviewAverage(reviewStar);
        }
        else{
            log.info("새로 업데이트");
            productReviewAverageRepository.save(productReviewAverage);
        }

        ResponseReviewStarDto responseReviewStarDto = new ResponseReviewStarDto(reviewStar.getAverageStarRating(),reviewStar.getFiveStar(),
                reviewStar.getFourStar(),reviewStar.getThreeStar(),reviewStar.getTwoStar(),reviewStar.getOneStar());


        return responseReviewStarDto;

    }
}
