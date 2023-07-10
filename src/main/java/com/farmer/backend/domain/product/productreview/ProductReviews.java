package com.farmer.backend.domain.product.productreview;

import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.orderproduct.OrderProduct;
import com.farmer.backend.domain.orders.Orders;
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
public class ProductReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;

    @NotNull
    private int fiveStarRating;

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


}
