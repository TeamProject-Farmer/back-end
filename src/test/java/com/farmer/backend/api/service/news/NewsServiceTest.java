package com.farmer.backend.api.service.news;

import com.farmer.backend.api.controller.user.news.response.ResponseNewsDto;
import com.farmer.backend.domain.news.NewsRepository;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
class NewsServiceTest {

    @Autowired
    NewsRepository newsRepository;

    @Test
    @DisplayName("메인 페이지 뉴스 기사 조회")
    void newsInfo() {
        ResponseNewsDto responseNewsDto = newsRepository.findNews().map(ResponseNewsDto::newsInfo).orElseThrow(() -> new CustomException(ErrorCode.NEWS_NOT_FOUND));
        log.info("responseNewsDto={}", responseNewsDto.getSubject());
        assertThat(responseNewsDto.getSubject()).isNotNull();
    }
}