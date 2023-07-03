package com.farmer.backend.api.controller.news;

import com.farmer.backend.api.controller.news.response.ResponseNewsDto;
import com.farmer.backend.api.service.news.NewsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/news")
@Tag(name = "NewsController", description = "기사 API")
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public List<ResponseNewsDto> newsInfo() {
        return newsService.findNewsInfo();
    }

}
