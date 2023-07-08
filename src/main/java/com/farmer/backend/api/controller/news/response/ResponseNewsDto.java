package com.farmer.backend.api.controller.news.response;

import com.farmer.backend.domain.news.News;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseNewsDto {

    private String subject;
    private String content;
    private String imgUrl;

    public static ResponseNewsDto newsInfo(News news) {
        return ResponseNewsDto.builder()
                .subject(news.getNewsSubject())
                .content(news.getNewsContent())
                .imgUrl(news.getNewsImgUrl())
                .build();
    }
}
