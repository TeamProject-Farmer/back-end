package com.farmer.backend.api.controller.notice.request;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchNoticeCondition {

    String userName;
    String userEmail;
    String title;
}
