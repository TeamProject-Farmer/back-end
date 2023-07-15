package com.farmer.backend.api.controller.search.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SearchProductCondition {

    private String searchWord;
}
