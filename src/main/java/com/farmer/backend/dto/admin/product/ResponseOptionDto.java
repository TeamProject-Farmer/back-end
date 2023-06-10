package com.farmer.backend.dto.admin.product;

import com.farmer.backend.entity.Options;
import lombok.*;

@Getter
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
