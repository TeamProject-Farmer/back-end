package com.farmer.backend.domain.search;

import com.farmer.backend.api.controller.search.response.ResponseSearchProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchQueryRepository {

    Page<ResponseSearchProductDto> searchProduct(Pageable pageable,
                                                 String sortSearchCond,
                                                 String searchWord);
}
