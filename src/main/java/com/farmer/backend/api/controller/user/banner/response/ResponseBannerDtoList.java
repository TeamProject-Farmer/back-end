package com.farmer.backend.api.controller.user.banner.response;

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
    private String imgUrl;
    private String linkUrl;


}
