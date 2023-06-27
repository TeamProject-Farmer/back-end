package com.farmer.backend.api.controller.admin.faq.request;

import lombok.*;

@Data
public class SearchFaqCondition {

    private String userName;
    private String userEmail;
    private String categoryName;


}
