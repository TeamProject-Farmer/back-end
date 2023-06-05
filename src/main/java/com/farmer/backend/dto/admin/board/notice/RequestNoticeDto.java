package com.farmer.backend.dto.admin.board.notice;

import com.farmer.backend.entity.Member;
import com.farmer.backend.entity.Notice;
import com.farmer.backend.entity.Product;
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
