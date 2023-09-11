package com.farmer.backend.api.controller.user.qna;

import com.farmer.backend.api.controller.user.qna.request.RequestQnAWriteDto;
import com.farmer.backend.api.controller.user.qna.response.ResponseProductQnADto;
import com.farmer.backend.api.controller.user.qna.response.ResponseQnADetailDto;
import com.farmer.backend.api.service.qna.ProductQnAService;
import com.farmer.backend.config.ApiDocumentResponse;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.login.general.MemberAdapter;
import com.farmer.backend.paging.PageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name="QnAController",description = "QnA 도메인 API")
public class QnAController {

    private final ProductQnAService productQnAService;

    /**
     * 문의사항 리스트 출력
     */
    @ApiDocumentResponse
    @Operation(summary = "QnA 리스트 전체 출력",description = "상품 QnA 리스트를 전체 출력합니다.")
    @GetMapping("/main/qna/{productId}")
    public Page<ResponseProductQnADto> productQnA(PageRequest pageRequest,@PathVariable(name = "productId") Long productId){

        pageRequest.setSize(5);
        return productQnAService.productQnA(pageRequest.of(),productId);
    }

    /**
     * 문의사항 작성
     */
    @ApiDocumentResponse
    @Operation(summary = "QnA 작성", description = "상품 QnA 작성")
    @PostMapping("/member/qna/write")
    public ResponseEntity<String> qnaWrite(@AuthenticationPrincipal MemberAdapter memberAdapter, RequestQnAWriteDto requestQnAWriteDto){
        Member member = memberAdapter.getMember();
        return productQnAService.qnaWrite(member,requestQnAWriteDto);
    }

    /**
     * 문의사항 상세보기
     */
    @ApiDocumentResponse
    @Operation(summary = "문의사항 상세보기",description = "상품 문의사항 상세보기")
    @GetMapping("/main/productQna/{qnaId}")
    public ResponseQnADetailDto qnaRead(@PathVariable(name = "qnaId") Long qnaId, String memberEmail){

        return productQnAService.qnaRead(qnaId,memberEmail);
    }


    /**
     * 내 문의사항만 보기
     */

    @ApiDocumentResponse
    @Operation(summary = "내가 쓴 문의 사항",description = "내가 쓴 문의 사항 리스트 보기")
    @GetMapping("member/qna/mine/{productId}")
    public Page<ResponseProductQnADto> qnaMine(@AuthenticationPrincipal MemberAdapter memberAdapter, PageRequest pageRequest,
                                               @PathVariable(name = "productId") Long productId){

        pageRequest.setSize(5);
        String memberEmail = memberAdapter.getMember().getEmail();
        return productQnAService.qnaMine(pageRequest.of(),memberEmail,productId);
    }
}
