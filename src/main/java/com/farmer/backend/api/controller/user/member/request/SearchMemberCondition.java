package com.farmer.backend.api.controller.user.member.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchMemberCondition {

    private String userName;
    private String userId;

}
