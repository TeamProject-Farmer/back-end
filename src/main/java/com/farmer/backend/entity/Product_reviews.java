package com.farmer.backend.entity;

import com.farmer.backend.dto.admin.board.RequestBoardReviewDto;
import com.farmer.backend.dto.admin.board.ResponseBoardReviewDto;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Product_reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne//(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne //(fetch = LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @NotNull
    @Column(columnDefinition = "text")
    private String content;

    @Column(length = 255)
    private String imgUrl;

    @ColumnDefault("0")
    private int likeCount;

    @NotNull
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime createdDate;

    public ResponseBoardReviewDto reviewList(){
        return  ResponseBoardReviewDto.builder()
                .id(id)
                .member(member)
                .orders(orders)
                .content(content)
                .imgUrl(imgUrl)
                .likeCount(likeCount)
                .createdDate(createdDate)
                .build();
    }

    public void updateReview(RequestBoardReviewDto reviewDto){
        this.id=reviewDto.getId();
        this.member=reviewDto.getMember();
        this.orders=reviewDto.getOrders();
        this.content=reviewDto.getContent();
        this.imgUrl=reviewDto.getImgUrl();
        this.likeCount=reviewDto.getLikeCount();
        this.createdDate=reviewDto.getCreatedDate();
    }


}
