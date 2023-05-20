package com.farmer.backend.dto.admin.board;

import com.farmer.backend.entity.Member;
import com.farmer.backend.entity.Product;
import com.farmer.backend.entity.Qna;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseBoardQnADto {

    private Long id;

    private Member member;

    private Product product;

    private String subject;

    private String content;

    private String answer;

    private LocalDateTime qCreatedDate;

    private LocalDateTime aCreatedDate;


    @Builder
    public  ResponseBoardQnADto(Long id, Member member, Product product, String subject, String content, String answer, LocalDateTime qCreatedDate , LocalDateTime aCreatedDate){
        this.id=id;
        this.member=member;
        this.product=product;
        this.subject=subject;
        this.content=content;
        this.answer=answer;
        this.qCreatedDate=qCreatedDate;
        this.aCreatedDate=aCreatedDate;
    }

    //Entity -> ResponseQnADto
    public static ResponseBoardQnADto getQnA(Qna qna){
        return ResponseBoardQnADto.builder()
                .id(qna.getId())
                .member(qna.getMember())
                .product(qna.getProduct())
                .subject(qna.getSubject())
                .content(qna.getContent())
                .answer(qna.getAnswer())
                .qCreatedDate(qna.getQCreatedDate())
                .aCreatedDate(qna.getACreatedDate())
                .build();
    }

    //ResponseQnADto -> Entity
    public Qna toEntity(){
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
