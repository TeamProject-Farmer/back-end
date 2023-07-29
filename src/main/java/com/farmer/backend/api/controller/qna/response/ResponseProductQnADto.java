package com.farmer.backend.api.controller.qna.response;

import com.farmer.backend.domain.admin.qna.SecretQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class ResponseProductQnADto {

    private String memberName;
    private String productName;
    private String subject;
    private String content;
    private String answer;
    private SecretQuestion secretQuestion;
    private LocalDateTime qCreatedDate;

}
