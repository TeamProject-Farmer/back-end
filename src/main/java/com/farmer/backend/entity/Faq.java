package com.farmer.backend.entity;

import com.farmer.backend.dto.admin.board.faq.RequestFaqDto;
import com.farmer.backend.dto.admin.board.faq.ResponseFaqDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Faq extends BaseTimeEntity{

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
                .memberName(member.getUsername())
                .categoryName(faqCategory.getName())
                .question(question)
                .answer(answer)
                .imgLink(imgLink)
                .build();
    }

    public void addFaqAnswer(RequestFaqDto faqDto) {
        this.answer=faqDto.getAnswer();
    }

    public void updateFaq(RequestFaqDto faqDto, Member member, FaqCategory faqCategory) {
        this.id=faqDto.getId();
        this.member=member;
        this.faqCategory=faqCategory;
        this.question=faqDto.getQuestion();
        this.answer=faqDto.getAnswer();
        this.imgLink=faqDto.getImgLink();
    }
}
