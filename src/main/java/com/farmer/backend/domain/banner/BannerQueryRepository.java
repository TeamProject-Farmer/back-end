package com.farmer.backend.domain.banner;

import com.farmer.backend.api.controller.user.banner.response.ResponseBannerDtoList;

import java.util.List;

public interface BannerQueryRepository {

    List<ResponseBannerDtoList> bannerList();
}
