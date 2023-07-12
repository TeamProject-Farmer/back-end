package com.farmer.backend.api.controller.review.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
public class ResponseReviewStarDto {

    private double averageStarRating;
    private Long fiveStar;
    private Long fourStar;
    private Long threeStar;
    private Long twoStar;
    private Long oneStar;

    public ResponseReviewStarDto (double averageStarRating , Long fiveStar , Long fourStar, Long threeStar,
                                  Long twoStar, Long oneStar){
        this.averageStarRating=averageStarRating;
        this.fiveStar=fiveStar;
        this.fourStar=fourStar;
        this.threeStar=threeStar;
        this.twoStar=twoStar;
        this.oneStar=oneStar;
    }
}
