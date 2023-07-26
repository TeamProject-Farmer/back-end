package com.farmer.backend.domain.qna;

import com.farmer.backend.api.controller.qna.response.ResponseProductQnADto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQnAQueryRepository {

    Page<ResponseProductQnADto> productQnAList(Pageable pageable);

    Page<ResponseProductQnADto> qnaMineList(Pageable pageable,String memberEmail);
}
