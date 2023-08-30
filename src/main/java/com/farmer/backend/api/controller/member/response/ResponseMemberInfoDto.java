package com.farmer.backend.api.controller.member.response;

import com.farmer.backend.domain.member.Grade;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseMemberInfoDto {

    private String email;
    private String nickName;
    private Grade grade;
    private Long reviewCount;
    private Long qnaCount;
    private Long cumulativeAmount;
    private Long point;

    private LocalDateTime createdDate;


}
