package com.farmer.backend.dto.admin.board;

import com.farmer.backend.entity.Member;
import com.farmer.backend.entity.Product;
import com.farmer.backend.entity.Qna;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RequestBoardQnADto {

    private Long id;
    private Member member;

    private Long product_id;
    private Product product;
    private String subject;
    private String content;
    private String answer;
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime qCreatedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime aCreatedDate;

    @Builder
    public RequestBoardQnADto(Long id, Member member,  Long product_id, String subject, String content, String answer, LocalDateTime qCreatedDate, LocalDateTime aCreatedDate){
        this.id=id;
        this.member=member;
        this.product_id=product_id;
        this.subject=subject;
        this.content=content;
        this.answer=answer;
        this.qCreatedDate=qCreatedDate;
        this.aCreatedDate=aCreatedDate;

    }

    // RequestDto -> Entity
    public Qna toEntity(){
        return Qna.builder()
                .id(id)
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
