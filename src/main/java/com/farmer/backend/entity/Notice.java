package com.farmer.backend.entity;


import com.farmer.backend.dto.admin.board.notice.RequestNoticeDto;
import com.farmer.backend.dto.admin.board.notice.ResponseNoticeDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Column(length = 50)
    private String title;

    @NotNull
    @Column(columnDefinition = "text")
    private String content;

    @Column(length = 255)
    private String imgLink;


    public ResponseNoticeDto noticeList(){
        return ResponseNoticeDto.builder()
                .id(id)
                .memberId(member.getId())
                .memberName(member.getUsername())
                .memberEmail(member.getEmail())
                .title(title)
                .content(content)
                .imgLink(imgLink)
                .build();

    }

    public void updateNotice(RequestNoticeDto noticeDto,Member updatemember) {

        this.id=noticeDto.getId();
        this.member=updatemember;
        this.content=noticeDto.getContent();
        this.title=noticeDto.getTitle();
        this.imgLink= noticeDto.getImgLink();
    }
}
