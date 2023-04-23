package com.farmer.backend.repository.admin.member;

import com.farmer.backend.dto.admin.member.SearchMemberCondition;
import com.farmer.backend.entity.Member;
import com.farmer.backend.entity.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.farmer.backend.entity.QMember.member;

@Repository
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private JPAQueryFactory query;

    public MemberQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }


    @Override
    public List<Member> searchMemberList(SearchMemberCondition cond) {
        return query.selectFrom(member)
                    .where(likeUserId(cond.getUserId()), likeUsername(cond.getUsername()))
                    .fetch();
    }

    public BooleanExpression likeUserId(String userId) {
        return userId != null ? member.username.like(userId) : null;
    }

    public BooleanExpression likeUsername(String username) {
        return username != null ? member.username.like(username) : null;
    }
}
