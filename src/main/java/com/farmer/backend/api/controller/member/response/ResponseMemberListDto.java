package com.farmer.backend.api.controller.member.response;

import com.farmer.backend.domain.member.Grade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseMemberListDto {

    private String nickName;
    private String email;
    private Grade grade;
    private LocalDateTime createdDate;
    private Long cumulativeAmount;


}
