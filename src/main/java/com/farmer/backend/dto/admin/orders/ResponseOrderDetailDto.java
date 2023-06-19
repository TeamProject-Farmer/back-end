package com.farmer.backend.dto.admin.orders;

import com.farmer.backend.entity.Options;
import com.farmer.backend.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class ResponseOrderDetailDto {

    private Product product;
    private Integer count;
    private Long optionId;
    private String optionName;
    private Integer optionPrice;

}
