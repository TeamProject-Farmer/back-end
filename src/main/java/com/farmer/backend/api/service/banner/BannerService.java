package com.farmer.backend.api.service.banner;

import com.farmer.backend.api.controller.banner.response.ResponseBannerDtoList;
import com.farmer.backend.domain.banner.BannerQueryRepository;
import com.farmer.backend.domain.banner.BannerQueryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BannerService {

    private final BannerQueryRepository bannerQueryRepository;
    private final BannerQueryRepositoryImpl bannerQueryRepositoryImpl;

    @Transactional(readOnly = true)
    public List<ResponseBannerDtoList> bannerList() {

        List<ResponseBannerDtoList> bannerList = bannerQueryRepositoryImpl.bannerList();
        return bannerList;
    }
}
