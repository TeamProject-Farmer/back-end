package com.farmer.backend.api.service.qna;

import com.farmer.backend.api.controller.qna.request.RequestQnAWriteDto;
import com.farmer.backend.api.controller.qna.response.ResponseProductQnADto;
import com.farmer.backend.api.controller.qna.response.ResponseQnADetailDto;
import com.farmer.backend.domain.admin.board.QnARepository;
import com.farmer.backend.domain.admin.qna.Qna;
import com.farmer.backend.domain.admin.qna.SecretQuestion;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.domain.product.Product;
import com.farmer.backend.domain.product.ProductRepository;
import com.farmer.backend.domain.qna.ProductQnAQueryRepositoryImpl;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductQnAService {
    private final ProductQnAQueryRepositoryImpl productQnAQueryRepositoryImpl;
    private final ProductRepository productRepository;
    private final QnARepository qnaRepository;

    @Transactional
    public Page<ResponseProductQnADto> productQnA(Pageable pageable) {
        return productQnAQueryRepositoryImpl.productQnAList(pageable);
    }

    @Transactional
    public ResponseEntity<String> qnaWrite(Member member,RequestQnAWriteDto requestQnAWriteDto) {

        Product product = productRepository.findById(requestQnAWriteDto.getProductId()).orElseThrow(()-> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        Qna qnaWrite = requestQnAWriteDto.toEntity(product,member);
        qnaRepository.save(qnaWrite);

        return new ResponseEntity<>("QNA 저장 완료", HttpStatus.OK);

    }

    @Transactional(readOnly = true)
    public ResponseQnADetailDto qnaRead(Long qnaId, String memberEmail) {

        System.out.println(memberEmail);
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(()-> new CustomException(ErrorCode.QNA_NOT_FOUND));

        if(qna.getSecretQuestion().name() == SecretQuestion.SECRET.name() && !qna.getMember().getEmail().equals(memberEmail)){
            throw new CustomException(ErrorCode.QNA_SECRET);
        }

        return ResponseQnADetailDto.getQnaDetail(qna);

    }

    @Transactional
    public Page<ResponseProductQnADto> qnaMine(Pageable pageable,String memberEmail) {

        return productQnAQueryRepositoryImpl.qnaMineList(pageable,memberEmail);
    }
}
