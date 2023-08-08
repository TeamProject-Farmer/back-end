package com.farmer.backend.api.controller.qna;

import com.farmer.backend.api.controller.qna.request.RequestQnAWriteDto;
import com.farmer.backend.api.controller.qna.response.ResponseProductQnADto;
import com.farmer.backend.api.controller.qna.response.ResponseQnADetailDto;
import com.farmer.backend.api.service.qna.ProductQnAService;
import com.farmer.backend.config.WithMockCustomUser;
import com.farmer.backend.domain.admin.qna.SecretQuestion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class QnAControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductQnAService productQnAService;

    @Test
    @DisplayName("문의사항 리스트 출력")
    void productQnA() {

        Pageable pageable = PageRequest.ofSize(5);
        Long productId = 6L;

        Page<ResponseProductQnADto> qnaList = productQnAService.productQnA(pageable,productId);

        for(ResponseProductQnADto qna :qnaList ){
            log.info(String.valueOf(qna.getQnaId()));
            log.info(qna.getSubject());
            log.info(qna.getContent());
        }
    }

    @Test
    @DisplayName("문의사항 작성")
    @WithMockCustomUser
    void qnaWrite() throws Exception{


        MvcResult result = mvc.perform(post("/api/main/login")
                        .param("password","0000")
                        .param("email","codms7020@naver.com"))
                .andExpect(status().isOk())
                .andReturn();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(result.getResponse().getContentAsString());
        String accessToken = (String) jsonObj.get("accessToken");

        String content = objectMapper
                .writeValueAsString(new RequestQnAWriteDto(5L, "상품상세문의", "재입고", SecretQuestion.SECRET,LocalDateTime.now()));


        mvc.perform(post("/api/member/qna/write")
                        .header("Authorization","Bearer "+accessToken)
                        .content(content)
                        .param("productId", String.valueOf(5))
                        .param("subject","상품상세문의")
                        .param("content","재입고")
                        .param("secretQuestion",SecretQuestion.GENERAL.name())
                        .param("qCreatedDate", String.valueOf(LocalDateTime.now())))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @DisplayName("문의사항 상세보기")
    void qnaRead() {

        ResponseQnADetailDto responseQnADetailDto = productQnAService
                .qnaRead(1L,"codms7020@naver.com");

        log.info(responseQnADetailDto.getSubject());
        log.info(responseQnADetailDto.getContent());
        log.info(String.valueOf(responseQnADetailDto.getQCreatedDate()));

    }

    @Test
    @DisplayName("내 문의사항만 보기")
    @WithMockCustomUser
    void qnaMine() throws Exception {

        MvcResult result = mvc.perform(post("/api/main/login")
                        .param("password","0000")
                        .param("email","codms7020@naver.com"))
                .andExpect(status().isOk())
                .andReturn();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(result.getResponse().getContentAsString());
        String accessToken = (String) jsonObj.get("accessToken");

        mvc.perform(get("/api/member/qna/mine")
                        .header("Authorization","Bearer "+accessToken))
                .andExpect(status().isOk())
                .andDo(print());

    }
}