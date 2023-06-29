package com.farmer.backend.api.controller.admin.notice.response;

import com.farmer.backend.domain.admin.notice.Notice;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseNoticeDto {

    private Long id;

    private Long memberId;

    private String memberName;

    private String memberEmail;

    private String title;

    private String content;

    private String imgLink;

    public static ResponseNoticeDto getNoticeList(Notice notice){
        return ResponseNoticeDto.builder()
                .id(notice.getId())
                .memberId(notice.getMember().getId())
                .memberEmail(notice.getMember().getEmail())
                .title(notice.getTitle())
                .content(notice.getContent())
                .imgLink(notice.getImgLink())
                .build();

    }
}
