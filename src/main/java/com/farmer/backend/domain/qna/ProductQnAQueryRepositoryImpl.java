package com.farmer.backend.domain.qna;

import com.farmer.backend.api.controller.qna.response.ResponseProductQnADto;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.farmer.backend.domain.admin.qna.QQna.qna;

@Repository
public class ProductQnAQueryRepositoryImpl implements ProductQnAQueryRepository {

    private final JPAQueryFactory query;
    public ProductQnAQueryRepositoryImpl(EntityManager em){
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<ResponseProductQnADto> productQnAList(Pageable pageable){
        List<ResponseProductQnADto> productQnAList = query
                .select(Projections.constructor(
                        ResponseProductQnADto.class,
                        qna.member.nickname,
                        qna.product.name,
                        qna.subject,
                        qna.content,
                        qna.answer,
                        qna.secretQuestion,
                        qna.qCreatedDate

                ))
                .from(qna)
                .orderBy(qna.qCreatedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count =query
                .select(qna.count())
                .from(qna)
                .fetchOne();

        return new PageImpl<>(productQnAList,pageable,count);
    }

}
