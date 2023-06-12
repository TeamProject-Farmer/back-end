package com.farmer.backend.dto.admin.board.faq;

import com.farmer.backend.entity.Faq;
import com.farmer.backend.entity.FaqCategory;
import com.farmer.backend.entity.Member;
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
