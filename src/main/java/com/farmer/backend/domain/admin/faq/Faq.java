package com.farmer.backend.domain.admin.faq;

import com.farmer.backend.domain.member.Member;
import com.farmer.backend.api.controller.user.faq.request.RequestFaqDto;
import com.farmer.backend.api.controller.user.faq.response.ResponseFaqDto;
import com.farmer.backend.domain.BaseTimeEntity;
import com.farmer.backend.domain.admin.faq.faqcategory.FaqCategory;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Faq extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private FaqCategory faqCategory;

    @NotNull
    @Column(length = 80)
    private String question;

    @NotNull
    @Column(length = 255)
    private String answer;

    @Column(length = 255)
    private String imgLink;

    public ResponseFaqDto faqList(){
        return ResponseFaqDto.builder()
                .id(id)
                .memberId(member.getId())
                .memberEmail(member.getEmail())
                .categoryName(faqCategory.getName())
                .question(question)
                .answer(answer)
                .imgLink(imgLink)
                .build();
    }

    public void faqAnswer(RequestFaqDto faqDto) {
        this.answer=faqDto.getAnswer();
    }


}
