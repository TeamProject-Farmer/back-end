package com.farmer.backend.api.controller.faq.request;

import com.farmer.backend.domain.admin.faq.Faq;
import com.farmer.backend.domain.admin.faq.faqcategory.FaqCategory;
import com.farmer.backend.domain.member.Member;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class RequestFaqDto {

    private Long id;
    private Long memberId;
    private Long category;
    private String question;
    private String answer;
    private String imgLink;

    public Faq toEntity(Member member, FaqCategory category){
        return Faq.builder()
                .id(id)
                .member(member)
                .faqCategory(category)
                .answer(answer)
                .question(question)
                .imgLink(imgLink)
                .build();
    }
}
