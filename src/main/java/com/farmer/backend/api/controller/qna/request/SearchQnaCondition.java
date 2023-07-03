package com.farmer.backend.api.controller.qna.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchQnaCondition {

    private String userName;
    private String userEmail;
    private String productName;

}
