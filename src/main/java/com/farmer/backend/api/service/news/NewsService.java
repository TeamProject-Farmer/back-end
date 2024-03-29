package com.farmer.backend.api.service.news;

import com.farmer.backend.api.controller.user.news.response.ResponseNewsDto;
import com.farmer.backend.domain.news.NewsRepository;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {

    private final NewsRepository newsRepository;

    /**
     * 메인 페이지 기사 정보
     * @return ResponseNewsDto
     */
    @Transactional(readOnly = true)
    public ResponseNewsDto findNewsInfo() {
        return newsRepository.findNews().map(ResponseNewsDto::newsInfo).orElseThrow(() -> new CustomException(ErrorCode.NEWS_NOT_FOUND));
    }
}
