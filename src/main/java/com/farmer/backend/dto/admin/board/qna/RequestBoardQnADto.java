package com.farmer.backend.dto.admin.board.qna;

import com.farmer.backend.entity.Member;
import com.farmer.backend.entity.Product;
import com.farmer.backend.entity.Qna;
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
