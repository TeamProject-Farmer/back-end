package com.farmer.backend.api.controller.banner;

import com.farmer.backend.api.controller.banner.response.ResponseBannerDtoList;
import com.farmer.backend.api.service.banner.BannerService;
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
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/main")
@Tag(name = "BannerController", description = "배너 API")
public class BannerController {


    private final BannerService bannerService;
    /**
     * 배너 전체 리스트
     */
    @ApiDocumentResponse
    @Operation(summary = "배너 전체 리스트",description = "배너 전체 리스트를 출력합니다.")
    @GetMapping("/banner")
    public List<ResponseBannerDtoList> bannerList(){
        return bannerService.bannerList();
    }
}
