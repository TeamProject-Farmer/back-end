package com.farmer.backend.api.controller.user.review.request;

import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.orderproduct.OrderProduct;
import com.farmer.backend.domain.product.productreview.ProductReviews;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RequestReviewWriteDto {

    private int fiveStarRating;
    private String content;
    private MultipartFile reviewImage;

    public ProductReviews toEntity(OrderProduct product, Member member,String imgUrl){
        return ProductReviews.builder()
                .member(member)
                .orderProduct(product)
                .fiveStarRating(fiveStarRating)
                .content(content)
                .imgUrl(imgUrl)
                .likeCount(0)
                .createdDate(LocalDateTime.now())
                .build();
    }
}
