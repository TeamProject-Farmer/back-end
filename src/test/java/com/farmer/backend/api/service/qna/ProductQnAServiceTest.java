package com.farmer.backend.api.service.qna;

import com.farmer.backend.api.controller.qna.request.RequestQnAWriteDto;
import com.farmer.backend.api.controller.qna.response.ResponseProductQnADto;
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
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import static com.farmer.backend.domain.admin.qna.SecretQuestion.SECRET;

@SpringBootTest
@Transactional
@Slf4j
class ProductQnAServiceTest {

    @Autowired
    ProductQnAQueryRepositoryImpl productQnAQueryRepositoryImpl;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    QnARepository qnaRepository;

    @Test
    @DisplayName("문의사항 리스트 보기")
    void qnaList (){

        Pageable pageable = PageRequest.ofSize(5);
        Long productId = 6L;
        Page<ResponseProductQnADto> qnaList = productQnAQueryRepositoryImpl.productQnAList(pageable,productId);

        for(ResponseProductQnADto qna : qnaList){
            log.info(String.valueOf(qna.getQnaId()));
            log.info(qna.getProductName());
            log.info(qna.getSubject());
            log.info(qna.getContent());
        }
    }

    @Test
    @DisplayName("문의사항 작성")
    void qnaWrite(){

        Member member = memberRepository.findByEmail("codms7020@naver.com").orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Product product = productRepository.findById(5L).orElseThrow(()-> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        RequestQnAWriteDto requestQnAWriteDto = new RequestQnAWriteDto(5L,"상품상세문의","재입고 언제되나요",SECRET,LocalDateTime.now());

        Qna qnaWrite = requestQnAWriteDto.toEntity(product,member);
        qnaRepository.save(qnaWrite);

    }

    @Test
    @DisplayName("문의사항 상세보기")
    void qnaRead() {

        Member member = memberRepository.findByEmail("codms7020@naver.com").orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Qna qna = qnaRepository.findById(1L).orElseThrow(()-> new CustomException(ErrorCode.QNA_NOT_FOUND));

        if(qna.getSecretQuestion().name().equals(SecretQuestion.SECRET.name()) && !qna.getMember().getEmail().equals(member.getEmail())){
            log.info("내가 쓴 비밀글만 볼 수 있습니다.");
            throw new CustomException(ErrorCode.QNA_SECRET);
        }

        log.info(String.valueOf(qna.getProduct().getId()));
        log.info(qna.getMember().getEmail());
        log.info(qna.getSubject());
        log.info(qna.getContent());
        log.info(String.valueOf(qna.getQCreatedDate()));
        log.info(qna.getAnswer());
        log.info(qna.getSecretQuestion().getName());


    }

    @Test
    @DisplayName("내가 쓴 문의사항")
    void qnaMine(){

        Pageable pageable = PageRequest.ofSize(5);
        Member member = memberRepository.findByEmail("codms7020@naver.com").orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        String memberEmail = member.getEmail();
        Page<ResponseProductQnADto> qnaList  = productQnAQueryRepositoryImpl.qnaMineList(pageable,memberEmail);

        for(ResponseProductQnADto qna : qnaList){
            log.info(String.valueOf(qna.getQnaId()));
            log.info(qna.getProductName());
            log.info(qna.getSubject());
            log.info(qna.getContent());

        }
    }
}