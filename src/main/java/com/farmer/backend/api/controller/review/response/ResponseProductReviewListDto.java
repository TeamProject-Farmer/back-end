package com.farmer.backend.api.controller.review.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class ResponseProductReviewListDto {

    private String memberNickname;
    private int fiveStarRating;
    private LocalDateTime createdDate;
    private String productName;
    private String optionName;
    private String imgUrl;

    private int likeCount;
    private String content;


}
