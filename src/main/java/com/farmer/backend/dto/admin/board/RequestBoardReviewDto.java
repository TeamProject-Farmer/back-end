package com.farmer.backend.dto.admin.board;

import com.farmer.backend.entity.Member;
import com.farmer.backend.entity.Orders;
import com.farmer.backend.entity.Product_reviews;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RequestBoardReviewDto {

    private Long id;
    private Member member;
    private Long orders_id;
    private Orders orders;
    private String content;
    private String imgUrl;
    private Integer likeCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime createdDate;

    @Builder
    public RequestBoardReviewDto (Long id, Member member, Long orders_id, String content, String imgUrl, int likeCount, LocalDateTime createdDate){
        this.id=id;
        this.member=member;
        this.orders_id=orders_id;
        this.content=content;
        this.imgUrl=imgUrl;
        this.likeCount=likeCount;
        this.createdDate=createdDate;
    }

    //RequestDto-> Entity
    public Product_reviews toEntity(){
        return Product_reviews.builder()
                .id(id)
                .member(member)
                .orders(orders)
                .content(content)
                .imgUrl(imgUrl)
                .likeCount(likeCount)
                .createdDate(createdDate)
                .build();
    }

}
