package com.farmer.backend.dto.admin.board.notice;

import com.farmer.backend.entity.Notice;
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
                .memberName(notice.getMember().getUsername())
                .memberEmail(notice.getMember().getEmail())
                .title(notice.getTitle())
                .content(notice.getContent())
                .imgLink(notice.getImgLink())
                .build();

    }
}
