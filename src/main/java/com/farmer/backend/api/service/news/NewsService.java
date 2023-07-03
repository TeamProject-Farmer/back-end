package com.farmer.backend.api.service.news;

import com.farmer.backend.api.controller.news.response.ResponseNewsDto;
import com.farmer.backend.domain.news.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {

    private final NewsRepository newsRepository;

    @Transactional(readOnly = true)
    public List<ResponseNewsDto> findNewsInfo() {
        return newsRepository.findAll().stream().map(ResponseNewsDto::newsInfo).collect(Collectors.toList());
    }
}
