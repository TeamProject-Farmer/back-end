package com.farmer.backend.api.controller.member.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchMemberCondition {

    private String username;
    private String userId;

}
