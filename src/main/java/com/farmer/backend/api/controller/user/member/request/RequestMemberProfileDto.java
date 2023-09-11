package com.farmer.backend.api.controller.user.member.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RequestMemberProfileDto {


    private String password;
    private String nickname;


}
