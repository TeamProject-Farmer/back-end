package com.farmer.backend.api.controller.qna.response;


import com.farmer.backend.domain.admin.qna.Qna;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ResponseQnADetailDto {

    private String subject;
    private String content;
    private LocalDateTime qCreatedDate;
    private String answer;
    private LocalDateTime aCreatedDate;

    public static ResponseQnADetailDto getQnaDetail(Qna qna){
        return ResponseQnADetailDto.builder()
                .subject(qna.getSubject())
                .content(qna.getContent())
                .qCreatedDate(qna.getQCreatedDate())
                .answer(qna.getAnswer())
                .aCreatedDate(qna.getACreatedDate())
                .build();
    }


}
