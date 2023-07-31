package com.farmer.backend.api.controller.review.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class ResponseBestReviewListDto {

    private Long reviewId;
    private String memberNickName;
    private String imgUrl ;
    private String content;
    private int likeCount;
    private int fiveStarRatingq;
}
