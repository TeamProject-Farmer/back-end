package com.farmer.backend.dto.admin.board.qna;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchQnaCondition {

    private String userName;
    private String userEmail;
    private String productName;

}
