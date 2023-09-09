package com.farmer.backend.api.controller.user.qna.request;

import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.product.Product;
import com.farmer.backend.domain.admin.qna.Qna;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class RequestBoardQnADto {

    private Long memberId;
    private Long productId;
    private String subject;
    private String content;
    private String answer;
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime qCreatedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime aCreatedDate;

    // RequestDto -> Entity
    public Qna toEntity(Member member, Product product){
        return Qna.builder()
                .member(member)
                .product(product)
                .subject(subject)
                .content(content)
                .answer(answer)
                .qCreatedDate(qCreatedDate)
                .aCreatedDate(aCreatedDate)
                .build();
    }

}
