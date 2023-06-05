package com.farmer.backend.dto.admin.board.qna;

import com.farmer.backend.entity.Member;
import com.farmer.backend.entity.Product;
import com.farmer.backend.entity.Qna;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResponseBoardQnADto {

    private Long id;

    private String memberEmail;
    private String memberName;

    private String productName;

    private String subject;

    private String content;

    private String answer;

    private LocalDateTime qCreatedDate;

    private LocalDateTime aCreatedDate;



    //Entity -> ResponseQnADto
    public static ResponseBoardQnADto getQnA(Qna qna){
        return ResponseBoardQnADto.builder()
                .id(qna.getId())
                .memberEmail(qna.getMember().getEmail())
                .memberName(qna.getMember().getUsername())
                .productName(qna.getProduct().getName())
                .subject(qna.getSubject())
                .content(qna.getContent())
                .answer(qna.getAnswer())
                .qCreatedDate(qna.getQCreatedDate())
                .aCreatedDate(qna.getACreatedDate())
                .build();
    }

}
