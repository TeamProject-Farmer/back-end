package com.farmer.backend.api.controller.admin.member.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchMemberCondition {

    private String username;
    private String userId;

}
