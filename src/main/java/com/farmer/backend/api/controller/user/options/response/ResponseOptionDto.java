package com.farmer.backend.api.controller.user.options.response;

import com.farmer.backend.domain.options.Options;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResponseOptionDto {

    private Long id;
    private String optionName;
    private Integer optionPrice;

    public static ResponseOptionDto optionList(Options options) {
        return ResponseOptionDto.builder()
                .id(options.getId())
                .optionName(options.getOptionName())
                .optionPrice(options.getOptionPrice())
                .build();
    }
}
