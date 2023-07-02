package com.farmer.backend.api.controller.qna.response;

import com.farmer.backend.domain.admin.qna.Qna;
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
                .productName(qna.getProduct().getName())
                .subject(qna.getSubject())
                .content(qna.getContent())
                .answer(qna.getAnswer())
                .qCreatedDate(qna.getQCreatedDate())
                .aCreatedDate(qna.getACreatedDate())
                .build();
    }

}
