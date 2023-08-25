package com.farmer.backend.domain.member;
import com.farmer.backend.api.controller.member.request.SearchMemberCondition;
import com.farmer.backend.api.controller.member.response.ResponseMemberDto;
import com.farmer.backend.api.controller.member.response.ResponseMemberListDto;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

import static com.farmer.backend.domain.member.QMember.member;


@Repository
@Slf4j
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory query;

    public MemberQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

//    /**
//     * 회원 전체 리스트
//     * @param sortOrderCond
//     */
//    @Override
//    public List<Member> findAll(String sortOrderCond, SearchMemberCondition searchMemberCond) {
//
//        List<Member> memberList = query
//                .select(member)
//                .from(member)
//                .where(likeUserId(searchMemberCond.getUserId()))
//                .orderBy(sortOrderCondition(sortOrderCond))
//                .fetch();
//
//        Long count = query
//                .select(member.count())
//                .from(member)
//                .fetchOne();
//
//        return memberList;
//    }

    /**
     * 회원 전체 리스트 (admin)
     * @param sortOrderCond 정렬값
     * @param searchMemberCond 검색값
     * @return
     */
    @Override
    public List<ResponseMemberListDto> memberList(String sortOrderCond, String searchMemberCond){

        return query
                .select(Projections.constructor(
                        ResponseMemberListDto.class,
                        member.nickname,
                        member.email,
                        member.grade,
                        member.createdDate,
                        member.cumulativeAmount
                )
                )
                .from(member)
                .fetch();

    }

    @Override
    public void deleteMember(Long memberId) {
        query
                .update(member)
                .set(member.accountStatus, AccountStatus.LEAVE)
                .where(member.id.eq(memberId))
                .execute();
    }

    /**
     * 회원 검색 리스트(이름, 아이디)
     * @param pageable
     * @param cond
     */
    @Override
    public Page<Member> searchMemberList(Pageable pageable, SearchMemberCondition cond) {
        List<Member> memberList = query.selectFrom(member)
                .where(likeUserId(cond.getUserId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(member.id.desc())
                .fetch();

        Long count = query
                .select(member.count())
                .from(member)
                .where(likeUserId(cond.getUserId()))
                .fetchOne();

        return new PageImpl<>(memberList, pageable, count);
    }

    /**
     * 관리자 권한 계정 리스트
     * @param pageable
     * @param sortOrderCond
     * @param searchMemberCond
     */
    @Override
    public Page<Member> getManagerList(Pageable pageable, String sortOrderCond, SearchMemberCondition searchMemberCond) {
        List<Member> managerList = query
                .select(member)
                .from(member)
                .where(likeUserId(searchMemberCond.getUserId()), member.role.ne(UserRole.USER))
                .orderBy(sortOrderCondition(sortOrderCond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query
                .select(member.count())
                .from(member)
                .where(likeUserId(searchMemberCond.getUserId()), member.role.ne(UserRole.USER))
                .fetchOne();

        return new PageImpl<>(managerList, pageable, count);
    }

    /**
     * 관리자 권한 계정 검색
     * @param pageable
     * @param cond
     */
    @Override
    public Page<Member> searchManagerList(Pageable pageable, SearchMemberCondition cond) {
        List<Member> resultList = query
                .select(member)
                .from(member)
                .where(likeUserId(cond.getUserId()), member.role.ne(UserRole.USER))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(member.id.desc())
                .fetch();

        Long count = query
                .select(member.count())
                .from(member)
                .where(likeUserId(cond.getUserId()))
                .fetchOne();

        return new PageImpl<>(resultList, pageable, count);
    }


    public OrderSpecifier<?> sortOrderCondition(String sortOrderCond) {

        Order order = Order.DESC;
        log.info(sortOrderCond);
        if (Objects.isNull(sortOrderCond) || sortOrderCond.equals("createdDate")) {
            return new OrderSpecifier<>(order, member.id);
        } else if (sortOrderCond.equals("grade")) {
            return new OrderSpecifier<>(order, member.grade);
        }

        return new OrderSpecifier(Order.DESC, NullExpression.DEFAULT, OrderSpecifier.NullHandling.Default);
    }

    public BooleanExpression likeUserId(String userId) {
        return userId != null ? member.email.contains(userId) : null;
    }





}
