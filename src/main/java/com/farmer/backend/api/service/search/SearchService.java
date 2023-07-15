package com.farmer.backend.api.service.search;

import com.farmer.backend.api.controller.search.request.SearchProductCondition;
import com.farmer.backend.api.controller.search.response.ResponseSearchProductDto;
import com.farmer.backend.domain.search.SearchQueryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    private final SearchQueryRepositoryImpl searchQueryRepositoryImpl;

    /**
     * 상품 검색
     * @param pageable 페이징
     * @param sortSearchCond 정렬값 (recent - 신상품순, review - 리뷰 많은 순 , low - 낮은 가격 순, high - 높은 가격 순)
     * @param searchProductCondition 검색어
     * @return 검색 상품
     */
    @Transactional(readOnly = true)
    public Page<ResponseSearchProductDto> searchProduct(Pageable pageable,
                                                        String sortSearchCond,
                                                        SearchProductCondition searchProductCondition) {

        return searchQueryRepositoryImpl.searchProduct(pageable,sortSearchCond, searchProductCondition.getSearchWord());
    }

}
