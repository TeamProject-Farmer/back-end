package com.farmer.backend.dto.admin.board;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchQnaCondition {

    private String username;
    private String userId;
    private String qnaId;

}
