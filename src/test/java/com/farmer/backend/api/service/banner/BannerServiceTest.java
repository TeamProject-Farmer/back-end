package com.farmer.backend.api.service.banner;

import com.farmer.backend.api.controller.user.banner.response.ResponseBannerDtoList;
import com.farmer.backend.domain.banner.BannerQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(value = false)
class BannerServiceTest {

    @Autowired
    BannerQueryRepository bannerQueryRepositoryImpl;

    @Test
    @DisplayName("배너 전체 리스트")
    void bannerList() {

        List<ResponseBannerDtoList> bannerList = bannerQueryRepositoryImpl.bannerList();
        for (ResponseBannerDtoList banner : bannerList) {
            log.info("banner={}",banner);
        }

    }
}