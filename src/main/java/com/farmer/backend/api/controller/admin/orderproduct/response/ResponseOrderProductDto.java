package com.farmer.backend.api.controller.admin.orderproduct.response;

import com.farmer.backend.api.controller.user.options.response.ResponseOptionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResponseOrderProductDto {

    private String productName;
    private String imgUrl;

    private List<ResponseOptionDto> options;
}
