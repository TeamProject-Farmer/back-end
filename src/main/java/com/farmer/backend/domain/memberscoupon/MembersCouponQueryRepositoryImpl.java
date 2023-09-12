package com.farmer.backend.domain.memberscoupon;

import com.farmer.backend.api.controller.user.coupon.response.ResponseMembersCouponDto;
import com.farmer.backend.api.controller.user.coupon.response.ResponseUseCouponListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.farmer.backend.domain.memberscoupon.QMembersCoupon.membersCoupon;

@Repository
public class MembersCouponQueryRepositoryImpl implements MembersCouponQueryRepository {

    private final JPAQueryFactory query;
    public MembersCouponQueryRepositoryImpl(EntityManager em){
        this.query=new JPAQueryFactory(em);
    }

    @Override
    public List<ResponseMembersCouponDto> membersCouponList(String memberEmail){
        List<ResponseMembersCouponDto> memberCouponList = query
                .select(Projections.constructor(
                        ResponseMembersCouponDto.class,
                        membersCoupon.coupons.id,
                        membersCoupon.coupons.serialNumber,
                        membersCoupon.coupons.name,
                        membersCoupon.coupons.startDateTime,
                        membersCoupon.coupons.endDateTime
                        )
                )
                .from(membersCoupon)
                .where(membersCoupon.member.email.eq(memberEmail))
                .fetch();
        return memberCouponList;
    }

    @Override
    public List<ResponseUseCouponListDto> useCouponList(String memberEmail){
        List<ResponseUseCouponListDto> useMembersCoupon = query
                .select(Projections.constructor(
                        ResponseUseCouponListDto.class,
                        membersCoupon.coupons.id,
                        membersCoupon.id,
                        membersCoupon.coupons.name,
                        membersCoupon.coupons.benefits,
                        membersCoupon.coupons.discountPolicy,
                        membersCoupon.coupons.fixedPrice,
                        membersCoupon.coupons.rateAmount
                    )
                )
                .from(membersCoupon)
                .where(membersCoupon.member.email.eq(memberEmail))
                .fetch();

        return useMembersCoupon;
    }

}
