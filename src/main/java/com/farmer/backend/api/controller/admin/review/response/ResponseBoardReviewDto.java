package com.farmer.backend.api.controller.admin.review.response;

import com.farmer.backend.domain.product.productreview.ProductReviews;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseBoardReviewDto {

    private Long id;

    private String memberName;
    private String memberEmail;
    private String content;
    private List productName;
    private String imgUrl;

    private int likeCount;

    private LocalDateTime createdDate;

    public static ResponseBoardReviewDto getReview(ProductReviews reviews, List<String> productList){
        return ResponseBoardReviewDto.builder()
                .id(reviews.getId())
                .memberName(reviews.getMember().getUsername())
                .memberEmail(reviews.getMember().getEmail())
                .productName(productList)
                .content(reviews.getContent())
                .imgUrl(reviews.getImgUrl())
                .likeCount(reviews.getLikeCount())
                .createdDate(reviews.getCreatedDate())
                .build();
    }


}
