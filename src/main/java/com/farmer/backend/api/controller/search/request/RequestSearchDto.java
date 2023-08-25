package com.farmer.backend.api.controller.search.request;

import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.search.Search;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestSearchDto {

    private String memberEmail;
    private String searchWord;
    private String sortSearchCond;


    public Search toEntity(Member member){
        return Search.builder()
                .member(member)
                .searchWord(searchWord)
                .searchDate(LocalDateTime.now())
                .build();
    }

}
