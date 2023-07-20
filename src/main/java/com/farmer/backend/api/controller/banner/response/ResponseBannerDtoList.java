package com.farmer.backend.api.controller.banner.response;

import com.farmer.backend.domain.banner.Banner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class ResponseBannerDtoList {

    private Long id;
    private String name;
    private String linkUrl;
    private String imgUrl;


}
