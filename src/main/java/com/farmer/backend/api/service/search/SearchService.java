package com.farmer.backend.api.service.search;

import com.farmer.backend.api.controller.search.request.RequestSearchDto;
import com.farmer.backend.api.controller.search.response.ResponseSearchProductDto;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
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
     * @param pageable 페이징
     * @param sortSearchCond 정렬값 (recent - 신상품순, review - 리뷰 많은 순 , low - 낮은 가격 순, high - 높은 가격 순)
     * @param searchWord 검색어
     * @return 검색 상품
     */
    @Transactional
    public Page<ResponseSearchProductDto> searchProduct(String memberEmail,
                                            Pageable pageable,
                                            String sortSearchCond,
                                            String searchWord) {

        Page<ResponseSearchProductDto> searchProduct =
                searchQueryRepositoryImpl.searchProduct(pageable,sortSearchCond, searchWord.trim());

        if(memberEmail!=null && sortSearchCond==null){

            Member member = memberRepository.findByEmail(memberEmail).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

            RequestSearchDto memberSearchWordList = new RequestSearchDto(member, searchWord.trim());
            searchRepository.save(memberSearchWordList.toEntity(memberSearchWordList));

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
