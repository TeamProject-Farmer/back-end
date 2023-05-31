package com.farmer.backend.dto.admin.board.faq;


import com.farmer.backend.entity.Faq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ResponseFaqDto {

    private Long id;
    private Long memberId;
    private String memberName;
    private String memberEmail;
    private String categoryName;
    private String question;
    private String answer;
    private String imgLink;

    public static ResponseFaqDto getFaqList(Faq faq){
        return ResponseFaqDto.builder()
                .id(faq.getId())
                .memberId(faq.getMember().getId())
                .memberName(faq.getMember().getUsername())
                .memberEmail(faq.getMember().getEmail())
                .categoryName(faq.getFaqCategory().getName())
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .imgLink(faq.getImgLink())
                .build();
    }
}
