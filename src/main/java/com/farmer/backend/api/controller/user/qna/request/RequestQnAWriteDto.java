package com.farmer.backend.api.controller.user.qna.request;

import com.farmer.backend.domain.admin.qna.Qna;
import com.farmer.backend.domain.admin.qna.SecretQuestion;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.product.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class RequestQnAWriteDto {

    private Long productId;
    private String subject;
    private String content;
    private SecretQuestion secretQuestion;
    private LocalDateTime qCreatedDateTime;

    public Qna toEntity(Product product, Member member){
        return Qna.builder()
                .product(product)
                .member(member)
                .subject(subject)
                .content(content)
                .secretQuestion(secretQuestion)
                .qCreatedDate(LocalDateTime.now())
                .build();
    }



}
