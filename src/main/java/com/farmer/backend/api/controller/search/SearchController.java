package com.farmer.backend.api.controller.search;

import com.farmer.backend.api.controller.search.request.RequestSearchDto;
import com.farmer.backend.api.controller.search.response.ResponseSearchProductDto;
import com.farmer.backend.api.service.search.SearchService;
import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.login.general.MemberAdapter;
import com.farmer.backend.paging.PageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


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
    public Page<ResponseSearchProductDto> searchProduct(@Valid @RequestBody RequestSearchDto requestSearchDto,
                                                        PageRequest pageRequest){

        log.info(requestSearchDto.getMemberEmail());
        log.info(requestSearchDto.getSearchWord());
        log.info(requestSearchDto.getSortSearchCond());
        return searchService.searchProduct(requestSearchDto,pageRequest.of());
    }

    @ApiDocumentResponse
    @Operation(summary = "회원 최근 검색어 출력", description = "로그인한 회원의 최근 검색어를 출력합니다.")
    @GetMapping("/member/search/word")
    public List<String> searchWord(@AuthenticationPrincipal MemberAdapter memberAdapter){
        String memberEmail = memberAdapter.getMember().getEmail();

        return searchService.memberSearchWord(memberEmail);

    }

}
