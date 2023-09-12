package com.farmer.backend.domain.search;

import com.farmer.backend.api.controller.user.search.response.ResponseSearchProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchQueryRepository {

    Page<ResponseSearchProductDto> searchProduct(Pageable pageable,
                                                 String sortSearchCond,
                                                 String searchWord);

    List<String> memberSearchWordList(String memberEmail);
}
