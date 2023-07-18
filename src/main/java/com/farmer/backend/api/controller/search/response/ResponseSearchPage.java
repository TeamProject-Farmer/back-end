package com.farmer.backend.api.controller.search.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResponseSearchPage {

    List<String> memberSearchWord ;
    Page<ResponseSearchProductDto> searchProduct;
    public ResponseSearchPage(List<String> memberSearchWord, Page<ResponseSearchProductDto> searchProduct){
        this.memberSearchWord=memberSearchWord;
        this.searchProduct=searchProduct;
    }
}
