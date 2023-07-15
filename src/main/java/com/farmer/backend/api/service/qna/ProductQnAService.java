package com.farmer.backend.api.service.qna;

import com.farmer.backend.api.controller.qna.response.ResponseProductQnADto;
import com.farmer.backend.domain.qna.ProductQnAQueryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductQnAService {
    private final ProductQnAQueryRepositoryImpl productQnAQueryRepositoryImpl;
    public Page<ResponseProductQnADto> productQnA(Pageable pageable) {
        return productQnAQueryRepositoryImpl.productQnAList(pageable);
    }
}
