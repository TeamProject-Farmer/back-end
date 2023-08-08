package com.farmer.backend.api.controller.review.response;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class ResponseProductReviewListDto {

    private Long reviewId;
    private String memberNickname;
    private int fiveStarRating;
    private LocalDateTime createdDate;
    private String productName;
    private String optionName;
    private String imgUrl;
    private int likeCount;
    private String content;

//    public static ResponseProductReviewListDto getProductReviewList(ProductReviews productReviews){
//        return ResponseProductReviewListDto.builder()
//                .memberNickname(productReviews.getMember().getNickname())
//                .fiveStarRating(productReviews.getFiveStarRating())
//                .createdDate(productReviews.getCreatedDate())
//                .productName(productReviews.getOrderProduct().getProduct().getName())
//                .optionName(productReviews.getOrderProduct().getOptions().getOptionName())
//                .imgUrl(productReviews.getImgUrl())
//                .build();
//    }


}
