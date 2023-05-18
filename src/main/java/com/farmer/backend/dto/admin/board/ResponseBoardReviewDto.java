package com.farmer.backend.dto.admin.board;

import com.farmer.backend.entity.Member;
import com.farmer.backend.entity.Orders;
import com.farmer.backend.entity.Product_reviews;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseBoardReviewDto {

    private Long id;

    private Member member;

    private Orders orders;

    private String content;

    private String imgUrl;

    private int likeCount;

    private LocalDateTime createdDate;

    @Builder
    public ResponseBoardReviewDto(Long id, Member member, Orders orders, String content, String imgUrl, int likeCount , LocalDateTime createdDate){
        this.id=id;
        this.member=member;
        this.orders=orders;
        this.content=content;
        this.imgUrl=imgUrl;
        this.likeCount=likeCount;
        this.createdDate=createdDate;
    }

    //Entity -> ResponseReviewDto
    public static ResponseBoardReviewDto getReview(Product_reviews reviews){
        return ResponseBoardReviewDto.builder()
                .id(reviews.getId())
                .member(reviews.getMember())
                .orders(reviews.getOrders())
                .content(reviews.getContent())
                .imgUrl(reviews.getImgUrl())
                .likeCount(reviews.getLikeCount())
                .createdDate(reviews.getCreatedDate())
                .build();
    }

    //ResponseReviewDto-> Entity
    public Product_reviews toEntity(){
        return Product_reviews.builder()
                .member(member)
                .orders(orders)
                .content(content)
                .imgUrl(imgUrl)
                .likeCount(likeCount)
                .createdDate(createdDate)
                .build();
    }

}
