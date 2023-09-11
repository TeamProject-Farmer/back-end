package com.farmer.backend.api.controller.user.notice.request;

import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.admin.notice.Notice;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class RequestNoticeDto {

    private Long memberId;
    private  String title;
    private String content;
    private String imgLink;

    public Notice toEntity(Member member){
        return Notice.builder()
                .member(member)
                .title(title)
                .content(content)
                .imgLink(imgLink)
                .build();
    }

}
