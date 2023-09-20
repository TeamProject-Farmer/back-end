package com.farmer.backend.api.controller.user.product.response;

import com.querydsl.core.Tuple;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseReviewAndQnaDto {

    private Long productId;
    private Long reviewCount;
    private Long qnaCount;
    private double reviewAverage;

    public ResponseReviewAndQnaDto(Long productId, Long reviewCount, Long qnaCount, Double reviewAverage){
        this.productId =productId;
        this.reviewCount=reviewCount;
        this.qnaCount=qnaCount;
        this.reviewAverage=reviewAverage;
    }
}
