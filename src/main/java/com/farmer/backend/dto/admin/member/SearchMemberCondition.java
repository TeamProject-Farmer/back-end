package com.farmer.backend.dto.admin.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchMemberCondition {

    private String username;
    private String userId;

}
