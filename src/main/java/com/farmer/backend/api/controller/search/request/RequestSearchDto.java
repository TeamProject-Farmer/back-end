package com.farmer.backend.api.controller.search.request;

import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.search.Search;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestSearchDto {

    private Member member;
    private String searchWord;
    private LocalDateTime searchDate;

    @Builder
    public RequestSearchDto(Member member,String searchWord){
        this.member=member;
        this.searchWord=searchWord;
    }

    public Search toEntity(RequestSearchDto requestSearchDto){
        return Search.builder()
                .member(requestSearchDto.member)
                .searchWord(requestSearchDto.searchWord)
                .searchDate(LocalDateTime.now())
                .build();
    }

}
