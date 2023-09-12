package com.farmer.backend.domain.member;
import com.farmer.backend.api.controller.user.member.request.SearchMemberCondition;
import com.farmer.backend.api.controller.user.member.response.ResponseMemberInfoDto;
import com.farmer.backend.api.controller.user.member.response.ResponseMemberListDto;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

import static com.farmer.backend.domain.admin.qna.QQna.qna;
import static com.farmer.backend.domain.member.QMember.member;
import static com.farmer.backend.domain.product.productreview.QProductReviews.productReviews;


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
     */
    @Override
    public List<ResponseMemberListDto> memberList(String sortOrderCond, SearchMemberCondition searchMemberCond){

        return query
                .select(Projections.constructor(
                        ResponseMemberListDto.class,
                        member.nickname,
                        member.email,
                        member.grade,
                        member.createdDate,
                        member.cumulativeAmount
                ))
                .from(member)
                .where(likeUserId(searchMemberCond.getUserId()),likeUserName(searchMemberCond.getUserName()))
                .fetch();

    }

    /**
     * 특정 회원 정보 조회 (admin)
     * @param memberId 회원
     */
    public ResponseMemberInfoDto memberInfo(Long memberId){


        return query
                .select(Projections.constructor(
                        ResponseMemberInfoDto.class,
                        member.email,
                        member.nickname,
                        member.grade,
                        JPAExpressions.select(productReviews.count())
                                .from(productReviews)
                                .where(productReviews.member.id.eq(memberId))
                        ,
                        JPAExpressions.select(qna.count())
                                .from(qna)
                                .where(qna.member.id.eq(memberId)),
                        member.cumulativeAmount,
                        member.point,
                        member.createdDate
                ))
                .from(member)
                .where(member.id.eq(memberId))
                .fetchOne();
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

    public BooleanExpression likeUserName(String userName) {
        return userName != null ? member.nickname.contains(userName) : null;
    }





}
