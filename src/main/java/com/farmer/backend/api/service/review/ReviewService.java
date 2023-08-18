package com.farmer.backend.api.service.review;

import com.farmer.backend.api.controller.review.request.RequestReviewStarDto;
import com.farmer.backend.api.controller.review.request.RequestReviewWriteDto;
import com.farmer.backend.api.controller.review.response.ResponseBestReviewListDto;
import com.farmer.backend.api.controller.review.response.ResponseProductReviewListDto;
import com.farmer.backend.api.controller.review.response.ResponseReviewStarDto;
import com.farmer.backend.api.service.S3Service;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.domain.orderproduct.OrderProduct;
import com.farmer.backend.domain.orderproduct.OrderProductRepository;
import com.farmer.backend.domain.product.Product;
import com.farmer.backend.domain.product.ProductRepository;
import com.farmer.backend.domain.product.productreview.ProductReviewQueryRepositoryImpl;
import com.farmer.backend.domain.product.productreview.ProductReviewRepository;
import com.farmer.backend.domain.product.productreview.ProductReviews;
import com.farmer.backend.domain.product.productreviewstar.ProductReviewAverage;
import com.farmer.backend.domain.product.productreviewstar.ProductReviewAverageRepository;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ProductReviewQueryRepositoryImpl reviewQueryRepositoryImpl;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final ProductReviewRepository productReviewRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductReviewAverageRepository productReviewAverageRepository;
    private final S3Service s3Service;


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
     * @param sortOrderCond 정렬 값
     * @param reviewCond 베스트순
     * @param productId 상품 ID 값
     * @return Page<ResponseProductReviewListDto>
     */
    @Transactional(readOnly = true)
    public Page<ResponseProductReviewListDto> productReviewList(Pageable pageable , String sortOrderCond, Integer reviewCond, Long productId) {

        return reviewQueryRepositoryImpl.productReviewList(pageable,sortOrderCond,reviewCond,productId);
    }

    /**
     * 상품별 리뷰 별점 리스트
     * @param productId 상품 ID값
     * @return ResponseReviewStarDto
     */
    @Transactional
    public ResponseReviewStarDto reviewAverage(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        reviewQueryRepositoryImpl.productCount(productId);

        RequestReviewStarDto reviewStar = reviewQueryRepositoryImpl.fiveStars(productId);
        ProductReviewAverage productReviewAverage = reviewStar.toEntity(product);

        if(productReviewAverageRepository.findByProductId(productId).isPresent()){
            productReviewAverage.updateReviewAverage(reviewStar);
        }
        else{
            productReviewAverageRepository.save(productReviewAverage);
        }
        product.updateAverageStar(reviewStar.getAverageStarRating());

        return new ResponseReviewStarDto(reviewStar.getAverageStarRating(),reviewStar.getFiveStar(),
                reviewStar.getFourStar(),reviewStar.getThreeStar(),reviewStar.getTwoStar(),reviewStar.getOneStar());

    }

    /**
     * 상품별 리뷰 이미지 리스트
     * @param productId 상품 ID값
     */
    @Transactional(readOnly = true)
    public List<String> productReviewImg(Long productId) {

        return reviewQueryRepositoryImpl.productReviewImg(productId);
    }

    /**
     * 상품 리뷰 작성
     * @param orderProductId 주문상품 ID
     * @param requestReviewWriteDto 리뷰 내용
     */
    @Transactional
    public void reviewWrite(String memberEmail, Long orderProductId, RequestReviewWriteDto requestReviewWriteDto) throws IOException {

        String reviewImgUrl = null;

        OrderProduct product = orderProductRepository.findById(orderProductId).orElseThrow(()-> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        Member member = memberRepository.findByEmail(memberEmail).orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if(requestReviewWriteDto.getReviewImage() != null) {
            reviewImgUrl = s3Service.reviewImgUpload(requestReviewWriteDto.getReviewImage());
        }

        ProductReviews productReviews = requestReviewWriteDto.toEntity(product,member,reviewImgUrl);
        productReviewRepository.save(productReviews);
        product.getProduct().updateReviewCount();

    }

    /**
     * 리뷰 좋아요 누르기
     */
    @Transactional
    public void reviewLikeCount(Long reviewId) {

        ProductReviews productReview = productReviewRepository.findById(reviewId).orElseThrow(()->new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        productReview.updateLikeCount();

    }
}
