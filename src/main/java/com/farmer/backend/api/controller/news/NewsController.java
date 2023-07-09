package com.farmer.backend.api.controller.news;

import com.farmer.backend.api.controller.news.response.ResponseNewsDto;
import com.farmer.backend.api.service.news.NewsService;
import com.farmer.backend.config.ApiDocumentResponse;
import io.swagger.v3.oas.annotations.Operation;
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

    /**
     * 메인 페이지 기사 정보
     * @return
     */
    @ApiDocumentResponse
    @Operation(summary = "뉴스 정보 조회", description = "메인 페이지 뉴스 정보를 조회합니다.")
    @GetMapping
    public ResponseNewsDto newsInfo() {
        return newsService.findNewsInfo();
    }

}
