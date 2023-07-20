package com.farmer.backend.api.controller.search;

import com.farmer.backend.api.controller.search.response.ResponseSearchPage;
import com.farmer.backend.api.service.search.SearchService;
import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.paging.PageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
@Tag(name="SearchController",description = "검색 도메인 API")
public class SearchController {

    private final SearchService searchService;


    @ApiDocumentResponse
    @Operation(summary = "검색 상품 출력" , description = "검색어에 해당되는 상품을 출력합니다.")
    @PostMapping("/main/search")
    public ResponseSearchPage searchProduct(String memberEmail,
                                            PageRequest pageRequest,
                                            String sortSearchCond,
                                            String searchWord){

        return searchService.searchProduct(memberEmail,pageRequest.of(),sortSearchCond, searchWord);
    }


}
