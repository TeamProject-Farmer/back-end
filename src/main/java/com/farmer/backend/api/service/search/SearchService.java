package com.farmer.backend.api.service.search;

import com.farmer.backend.api.controller.user.search.request.RequestSearchDto;
import com.farmer.backend.api.controller.user.search.response.ResponseSearchProductDto;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.domain.search.Search;
import com.farmer.backend.domain.search.SearchQueryRepositoryImpl;
import com.farmer.backend.domain.search.SearchRepository;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    private final SearchQueryRepositoryImpl searchQueryRepositoryImpl;
    private final SearchRepository searchRepository;
    private final MemberRepository memberRepository;


    /**
     * 상품 검색
     * @param requestSearchDto 검색 DTO
     * @param pageable 페이징
     */
    @Transactional
    public Page<ResponseSearchProductDto> searchProduct(RequestSearchDto requestSearchDto,
                                            Pageable pageable) {

        Page<ResponseSearchProductDto> searchProduct =
                searchQueryRepositoryImpl.searchProduct(pageable,requestSearchDto.getSortSearchCond(), requestSearchDto.getSearchWord().trim());

        if(requestSearchDto.getMemberEmail()!=null && requestSearchDto.getSortSearchCond()==null){

            Member member = memberRepository.findByEmail(requestSearchDto.getMemberEmail()).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

            Search memberSearchWordList = requestSearchDto.toEntity(member);
            searchRepository.save(memberSearchWordList);

        }
        return searchProduct;
    }

    /**
     * 회원 최근 검색어
     * @param memberEmail 회원 이메일
     * @return 회원 최근 검색어
     */
    @Transactional
    public List<String> memberSearchWord(String memberEmail){

        return searchQueryRepositoryImpl.memberSearchWordList(memberEmail);

    }

}
