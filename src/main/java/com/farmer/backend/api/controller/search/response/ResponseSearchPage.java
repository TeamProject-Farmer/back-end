package com.farmer.backend.api.controller.search.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseSearchPage {

    List<String> memberSearchWord ;
    Page<ResponseSearchProductDto> searchProduct;

}
