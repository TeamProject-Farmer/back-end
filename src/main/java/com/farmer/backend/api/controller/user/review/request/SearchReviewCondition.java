package com.farmer.backend.api.controller.user.review.request;

import lombok.Data;

@Data
public class SearchReviewCondition {

    private String productName;
    private String userName;
    private String userEmail;



}
