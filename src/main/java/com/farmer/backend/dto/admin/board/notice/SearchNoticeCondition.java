package com.farmer.backend.dto.admin.board.notice;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchNoticeCondition {

    String userName;
    String userEmail;
    String title;
}
